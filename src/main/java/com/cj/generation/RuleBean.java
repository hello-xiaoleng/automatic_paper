/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

/**
 * @author xuanpu
 * @version RuleBean.java, v 0.1 2023年01月31日 9:22 PM xuanpu
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 组卷规则Bean
 *
 * @author: xlli
 * @CreateDate: 2016-4-28 下午05:02:05
 * @version: 1.0
 */
public class RuleBean {
    /**
     * 规则id
     */
    private long   id;
    /**
     * 规则对应的考试id
     */
    private long   examId;
    /**
     * 试卷总分
     */
    private int    totalMark;
    /**
     * 试卷期望难度系数
     */
    private double difficulty;
    /**
     * 单选题数量
     */
    private int    singleNum;
    /**
     * 多选题数量
     */
    private int    multipleNum;
    /**
     * 多选题分数
     */
    private int    multipleScore;

    /**
     * 单选题单个分值
     */
    private double       singleScore;
    /**
     * 填空题数量
     */
    private int          completeNum;
    /**
     * 填空题单个分值
     */
    private double       completeScore;
    /**
     * 主观题数量
     */
    private int          subjectiveNum;
    /**
     * 主观题单个分值
     */
    private double       subjectiveScore;
    /**
     * 试卷包含的知识点id
     */
    private List<String> pointIds;
    /**
     * 规则创建时间
     */
    private Date         createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public double getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(double singleScore) {
        this.singleScore = singleScore;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public double getCompleteScore() {
        return completeScore;
    }

    public void setCompleteScore(double completeScore) {
        this.completeScore = completeScore;
    }

    public int getSubjectiveNum() {
        return subjectiveNum;
    }

    public void setSubjectiveNum(int subjectiveNum) {
        this.subjectiveNum = subjectiveNum;
    }

    public double getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(double subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public List<String> getPointIds() {
        return pointIds;
    }

    public void setPointIds(String pointIds) {
        // 是否是表单传过来的数据
        if (pointIds.endsWith("#")) {
            pointIds = pointIds.substring(0, pointIds.lastIndexOf(","));
        }
        // 使用HashSet去重
        this.pointIds = new ArrayList<>(new HashSet<>(Arrays.asList(pointIds.split(","))));
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property <tt>multipleNum</tt>.
     *
     * @return property value of multipleNum
     */
    public int getMultipleNum() {
        return multipleNum;
    }

    /**
     * Setter method for property <tt>multipleNum </tt>.
     *
     * @param multipleNum value to be assigned to property multipleNum
     */
    public void setMultipleNum(int multipleNum) {
        this.multipleNum = multipleNum;
    }

    /**
     * Getter method for property <tt>multipleScore</tt>.
     *
     * @return property value of multipleScore
     */
    public int getMultipleScore() {
        return multipleScore;
    }

    /**
     * Setter method for property <tt>multipleScore </tt>.
     *
     * @param multipleScore value to be assigned to property multipleScore
     */
    public void setMultipleScore(int multipleScore) {
        this.multipleScore = multipleScore;
    }

    @Override
    public String toString() {
        return "RuleBean{" +
                "totalMark=" + totalMark +
                ", difficulty=" + difficulty +
                ", singleNum=" + singleNum +
                ", multipleNum=" + multipleNum +
                ", multipleScore=" + multipleScore +
                ", singleScore=" + singleScore +
                ", completeNum=" + completeNum +
                ", completeScore=" + completeScore +
                ", subjectiveNum=" + subjectiveNum +
                ", subjectiveScore=" + subjectiveScore +
                ", pointIds=" + pointIds +
                '}';
    }

    private double T = 100;

}
