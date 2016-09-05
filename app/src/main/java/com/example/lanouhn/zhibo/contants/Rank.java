package com.example.lanouhn.zhibo.contants;

import java.util.List;

/**
 * Created by lanouhn on 16/9/1.
 */
public class Rank {

    private List<Ranks> ranks;

    public List<Ranks> getRanks() {
        return ranks;
    }

    public void setRanks(List<Ranks> ranks) {
        this.ranks = ranks;
    }

    public static class Ranks{

        private int trend;
        private int count;
        private int userId;
        private String userName;
        private String avatar;
        private int grade;

        public int getTrend() {
            return trend;
        }

        public void setTrend(int trend) {
            this.trend = trend;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }
}
