package com.example.demo.domain;

public class UserScore {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_score.id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_score.user_id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_score.user_score
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    private Long userScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_score.name
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_score.id
     *
     * @return the value of user_score.id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_score.id
     *
     * @param id the value for user_score.id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_score.user_id
     *
     * @return the value of user_score.user_id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_score.user_id
     *
     * @param userId the value for user_score.user_id
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_score.user_score
     *
     * @return the value of user_score.user_score
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public Long getUserScore() {
        return userScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_score.user_score
     *
     * @param userScore the value for user_score.user_score
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public void setUserScore(Long userScore) {
        this.userScore = userScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_score.name
     *
     * @return the value of user_score.name
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_score.name
     *
     * @param name the value for user_score.name
     *
     * @mbggenerated Tue Apr 30 10:16:46 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}