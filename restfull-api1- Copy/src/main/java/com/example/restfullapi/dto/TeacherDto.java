package com.example.restfullapi.dto;
public class TeacherDto {

        private int id;

        private String name;

        private String gmail;

        private  int age;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public String getGmail() {
                return gmail;
        }

        public void setGmail(String gmail) {
                this.gmail = gmail;
        }

}
