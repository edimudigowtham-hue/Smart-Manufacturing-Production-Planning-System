package com.genc.smpps.model;

public class MachineOee {

    private final int machineId;
    private final double totalRuntime;
    private final double totalDowntime;
    private final long totalPieces;
    private final long goodPieces;
    private final double availability;
    private final double performance;
    private final double quality;
    private final double oee;

    public MachineOee(int machineId, double totalRuntime, double totalDowntime,
                      long totalPieces, long goodPieces,
                      double availability, double performance, double quality, double oee) {
        this.machineId = machineId;
        this.totalRuntime = totalRuntime;
        this.totalDowntime = totalDowntime;
        this.totalPieces = totalPieces;
        this.goodPieces = goodPieces;
        this.availability = availability;
        this.performance = performance;
        this.quality = quality;
        this.oee = oee;
    }

    public int getMachineId() {
        return machineId;
    }

    public double getTotalRuntime() {
        return totalRuntime;
    }

    public double getTotalDowntime() {
        return totalDowntime;
    }

    public long getTotalPieces() {
        return totalPieces;
    }

    public long getGoodPieces() {
        return goodPieces;
    }

    public double getAvailability() {
        return availability;
    }

    public double getPerformance() {
        return performance;
    }

    public double getQuality() {
        return quality;
    }

    public double getOee() {
        return oee;
    }

    public String getAvailabilityFormatted() {
        return String.format("%.2f %%", availability);
    }

    public String getPerformanceFormatted() {
        return String.format("%.2f %%", performance);
    }

    public String getQualityFormatted() {
        return String.format("%.2f %%", quality);
    }

    public String getOeeFormatted() {
        return String.format("%.2f %%", oee);
    }
}

 
