package com.genc.smpps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class QualityInspection {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int inspectionId;

   private int orderId;
   private String inspectionDate;
   private int sampleSize;
   private int defectCount;
   private String inspectionResult; // PASS, FAIL, REWORK

   private String defectType;
   private String defectDescription;
   private String severity;

   public int getInspectionId() {
      return inspectionId;
   }

   public void setInspectionId(int inspectionId) {
      this.inspectionId = inspectionId;
   }

   public int getOrderId() {
      return orderId;
   }

   public void setOrderId(int orderId) {
      this.orderId = orderId;
   }

   public String getInspectionDate() {
      return inspectionDate;
   }

   public void setInspectionDate(String inspectionDate) {
      this.inspectionDate = inspectionDate;
   }

   public int getSampleSize() {
      return sampleSize;
   }

   public void setSampleSize(int sampleSize) {
      this.sampleSize = sampleSize;
   }

   public int getDefectCount() {
      return defectCount;
   }

   public void setDefectCount(int defectCount) {
      this.defectCount = defectCount;
   }

   public String getInspectionResult() {
      return inspectionResult;
   }

   public void setInspectionResult(String inspectionResult) {
      this.inspectionResult = inspectionResult;
   }

   public String getDefectType() {
      return defectType;
   }

   public void setDefectType(String defectType) {
      this.defectType = defectType;
   }

   public String getDefectDescription() {
      return defectDescription;
   }

   public void setDefectDescription(String defectDescription) {
      this.defectDescription = defectDescription;
   }

   public String getSeverity() {
      return severity;
   }

   public void setSeverity(String severity) {
      this.severity = severity;
   }
}
