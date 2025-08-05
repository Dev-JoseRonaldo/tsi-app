package com.example.tsi_app;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootCheck {

    public static boolean isDeviceRooted() {
        return checkBuildTags() || checkSuperUserApk() || checkSuExists() || checkWhichSu();
    }

    // Verifica se há tags de build de teste (indicador de root ou ROM customizada)
    private static boolean checkBuildTags() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    // Verifica se o app Superuser.apk existe
    private static boolean checkSuperUserApk() {
        return new File("/system/app/Superuser.apk").exists() ||
                new File("/system/app/SuperSU.apk").exists() ||
                new File("/system/xbin/su").exists();
    }

    // Verifica se o binário su existe em paths comuns
    private static boolean checkSuExists() {
        String[] paths = {
                "/system/bin/",
                "/system/xbin/",
                "/sbin/",
                "/system/sd/xbin/",
                "/system/bin/failsafe/",
                "/data/local/xbin/",
                "/data/local/bin/",
                "/data/local/"
        };

        for (String path : paths) {
            if (new File(path + "su").exists()) {
                return true;
            }
        }
        return false;
    }

    // Tenta executar o comando "which su"
    private static boolean checkWhichSu() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}
