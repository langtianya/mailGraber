package com.wangzhe.beans;

/**
 *
 * @author ocq
 */
public class UserBean {

    private String jId;
    private String userName;
    private String status;
    private String photo;
    private int unReadMessCount;

    public UserBean(String jId, String userName, String status,int unReadMessCount) {
       this.jId = jId;
       this.userName = userName;
       this.status = status;
       this.unReadMessCount = unReadMessCount;
    }

    /**
     * @return the jId
     */
    public String getJId() {
        return jId;
    }

    /**
     * @param jId the jId to set
     */
    public void setJId(String jId) {
        this.jId = jId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the unReadMessCount
     */
    public int getUnReadMessCount() {
        return unReadMessCount;
    }

    /**
     * @param unReadMessCount the unReadMessCount to set
     */
    public void setUnReadMessCount(int unReadMessCount) {
        this.unReadMessCount = unReadMessCount;
    }

  
}
