package org.parking.models;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminLogViewer extends LogViewer{

    @Override protected Path logFile() { return Paths.get("data", "admin.log"); }
}
