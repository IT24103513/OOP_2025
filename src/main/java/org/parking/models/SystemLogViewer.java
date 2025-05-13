package org.parking.models;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemLogViewer extends LogViewer{

    @Override protected Path logFile() { return Paths.get("data", "system.log"); }
}
