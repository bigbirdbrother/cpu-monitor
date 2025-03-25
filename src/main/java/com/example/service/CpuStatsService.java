package com.example.service;

import com.example.config.SshConfig;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Component
public class CpuStatsService {
    private final SshConfig sshConfig;

    public CpuStatsService(SshConfig sshConfig) {
        this.sshConfig = sshConfig;
    }

    public String getCpuStatsJson() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(
                sshConfig.username(),
                sshConfig.host(),
                sshConfig.port()
        );
        session.setPassword(sshConfig.password());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(sshConfig.connectTimeout());

        StringBuilder json = new StringBuilder("{");
        try {
            for (int cpu = 0; cpu < 6; cpu++) {
                json.append("\"cpu").append(cpu).append("\":[");
                for (int state = 0; state < 9; state++) {
                    json.append(executeCommand(session,
                            String.format("cat /sys/devices/system/cpu/cpu%d/cpuidle/state%d/usage", cpu, state)
                    ));
                    if (state < 8) json.append(",");
                }
                json.append("]").append(cpu < 5 ? "," : "");
            }
        } finally {
            session.disconnect();
        }
        return json.append("}").toString();
    }

    private String executeCommand(Session session, String command) throws JSchException {
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);

        try {
            channel.connect();
            return readOutput(channel);
        } finally {
            channel.disconnect();
        }
    }

    private String readOutput(ChannelExec channel) {
        try (InputStream in = channel.getInputStream();
             Scanner scanner = new Scanner(in).useDelimiter("\\A")) {

            return scanner.hasNext() ? scanner.next().trim() : "0";
        } catch (IOException e) {
            return "0";
        }
    }
}