package org.home;

import java.util.List;

public class DownloadList {
    private String name;
    private List<Download> downloads;

    public DownloadList() {}

    public DownloadList(String name, List<Download> downloads) {
        this.name = name;
        this.downloads = downloads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Download> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Download> downloads) {
        this.downloads = downloads;
    }
}

class Download {
    private String title;
    private String[] uris;
    private String uploadDate;
    private String diskSpace;

    public Download(String title, String[] uris, String uploadDate, String diskSpace) {
        this.title = title;
        this.uris = uris;
        this.uploadDate = uploadDate;
        this.diskSpace = diskSpace;
    }

    public String getTitle() {
        return title;
    }

    public String[] getUris() {
        return uris;
    }

    public void setUris(String[] uris) {
        this.uris = uris;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(String diskSpace) {
        this.diskSpace = diskSpace;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
