package com.cso.sikoling.abstraction.entity.dokumen;

import java.io.Serializable;
import java.util.Objects;


public class MetaFile implements Serializable {
    
    private String baseFileName;
    private boolean disablePrint;
    private String ownerId;
    private String postMessageOrigin;    
    private long size;
    private boolean supportsRename;
    private String templateSource; 
    private boolean userCanRename;
    private boolean userCanWrite;
    private boolean userCanNotWriteRelative;
    private String userId;
    private String userFriendlyName;
    private boolean isAdminUser;
    private boolean isAnonymousUser;
    private boolean enableInsertRemoteImage;
    private boolean enableRemoteLinkPicker;
    private boolean enableInsertRemoteFile;
    private boolean enableRemoteAIContent;
    private boolean disableInsertLocalImage;
    private boolean hidePrintOption;
    private boolean hideSaveOption;
    private boolean hideExportOption;
    private boolean disableExport;
    private boolean disableCopy;
    private boolean disableInactiveMessages;
    private boolean userCanOnlyComment;
    private boolean userCanOnlyManageRedlines;
    private boolean downloadAsPostMessage;
    private boolean saveAsPostmessage;
    private boolean enableOwnerTermination;   

    public MetaFile() {
    }
    
    public MetaFile(String baseFileName, String ownerId, long size, 
                                    String userId, String userFriendlyName) {
        this.baseFileName = baseFileName;
        this.disablePrint = false;
        this.ownerId = ownerId;
        this.postMessageOrigin = null;
        this.size = size;
        this.supportsRename = false;
        this.templateSource = null;
        this.userCanRename = false;
        this.userCanWrite = false;
        this.userCanNotWriteRelative = true;
        this.userId = userId;
        this.userFriendlyName = userFriendlyName;
        this.isAdminUser = true;
        this.isAnonymousUser = false;
        this.enableInsertRemoteImage = false;
        this.enableRemoteLinkPicker = false;
        this.enableInsertRemoteFile = false;
        this.enableRemoteAIContent = false;
        this.disableInsertLocalImage = false;
        this.hidePrintOption = false;
        this.hideSaveOption = false;
        this.hideExportOption = false;
        this.disableExport = true;
        this.disableCopy = false;
        this.disableInactiveMessages = true;
        this.userCanOnlyComment = false;
        this.userCanOnlyManageRedlines = false;
        this.downloadAsPostMessage = false;
        this.saveAsPostmessage = false;
        this.enableOwnerTermination = true;
    }

    public MetaFile(String baseFileName, boolean disablePrint, String ownerId, 
            String postMessageOrigin, long size, boolean supportsRename, 
            String templateSource, boolean userCanRename, boolean userCanNotWriteRelative, 
            String userId, String userFriendlyName, boolean isAdminUser, boolean isAnonymousUser, 
            boolean enableInsertRemoteImage, boolean enableRemoteLinkPicker, 
            boolean enableInsertRemoteFile, boolean enableRemoteAIContent, 
            boolean disableInsertLocalImage, boolean hidePrintOption, 
            boolean hideSaveOption, boolean hideExportOption, boolean disableExport, 
            boolean disableCopy, boolean disableInactiveMessages, boolean userCanOnlyComment, 
            boolean userCanOnlyManageRedlines, boolean downloadAsPostMessage, 
            boolean saveAsPostmessage, boolean enableOwnerTermination, boolean userCanWrite) {
        this.baseFileName = baseFileName;
        this.disablePrint = disablePrint;
        this.ownerId = ownerId;
        this.postMessageOrigin = postMessageOrigin;
        this.size = size;
        this.supportsRename = supportsRename;
        this.templateSource = templateSource;
        this.userCanRename = userCanRename;
        this.userCanWrite = userCanWrite;
        this.userCanNotWriteRelative = userCanNotWriteRelative;
        this.userId = userId;
        this.userFriendlyName = userFriendlyName;
        this.isAdminUser = isAdminUser;
        this.isAnonymousUser = isAnonymousUser;
        this.enableInsertRemoteImage = enableInsertRemoteImage;
        this.enableRemoteLinkPicker = enableRemoteLinkPicker;
        this.enableInsertRemoteFile = enableInsertRemoteFile;
        this.enableRemoteAIContent = enableRemoteAIContent;
        this.disableInsertLocalImage = disableInsertLocalImage;
        this.hidePrintOption = hidePrintOption;
        this.hideSaveOption = hideSaveOption;
        this.hideExportOption = hideExportOption;
        this.disableExport = disableExport;
        this.disableCopy = disableCopy;
        this.disableInactiveMessages = disableInactiveMessages;
        this.userCanOnlyComment = userCanOnlyComment;
        this.userCanOnlyManageRedlines = userCanOnlyManageRedlines;
        this.downloadAsPostMessage = downloadAsPostMessage;
        this.saveAsPostmessage = saveAsPostmessage;
        this.enableOwnerTermination = enableOwnerTermination;
    }

    public String getBaseFileName() {
        return baseFileName;
    }

    public boolean isDisablePrint() {
        return disablePrint;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getPostMessageOrigin() {
        return postMessageOrigin;
    }

    public long getSize() {
        return size;
    }

    public boolean isSupportsRename() {
        return supportsRename;
    }

    public String getTemplateSource() {
        return templateSource;
    }

    public boolean isUserCanRename() {
        return userCanRename;
    }

    public boolean isUserCanNotWriteRelative() {
        return userCanNotWriteRelative;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }

    public boolean isIsAdminUser() {
        return isAdminUser;
    }

    public boolean isIsAnonymousUser() {
        return isAnonymousUser;
    }

    public boolean isEnableInsertRemoteImage() {
        return enableInsertRemoteImage;
    }

    public boolean isEnableRemoteLinkPicker() {
        return enableRemoteLinkPicker;
    }

    public boolean isEnableInsertRemoteFile() {
        return enableInsertRemoteFile;
    }

    public boolean isEnableRemoteAIContent() {
        return enableRemoteAIContent;
    }

    public boolean isDisableInsertLocalImage() {
        return disableInsertLocalImage;
    }

    public boolean isHidePrintOption() {
        return hidePrintOption;
    }

    public boolean isHideSaveOption() {
        return hideSaveOption;
    }

    public boolean isHideExportOption() {
        return hideExportOption;
    }

    public boolean isDisableExport() {
        return disableExport;
    }

    public boolean isDisableCopy() {
        return disableCopy;
    }

    public boolean isDisableInactiveMessages() {
        return disableInactiveMessages;
    }

    public boolean isUserCanOnlyComment() {
        return userCanOnlyComment;
    }

    public boolean isUserCanOnlyManageRedlines() {
        return userCanOnlyManageRedlines;
    }

    public boolean isDownloadAsPostMessage() {
        return downloadAsPostMessage;
    }

    public boolean isSaveAsPostmessage() {
        return saveAsPostmessage;
    }

    public boolean isEnableOwnerTermination() {
        return enableOwnerTermination;
    }

    public boolean isUserCanWrite() {
        return userCanWrite;
    }

    public void setBaseFileName(String baseFileName) {
        this.baseFileName = baseFileName;
    }

    public void setDisablePrint(boolean disablePrint) {
        this.disablePrint = disablePrint;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setPostMessageOrigin(String postMessageOrigin) {
        this.postMessageOrigin = postMessageOrigin;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSupportsRename(boolean supportsRename) {
        this.supportsRename = supportsRename;
    }

    public void setTemplateSource(String templateSource) {
        this.templateSource = templateSource;
    }

    public void setUserCanRename(boolean userCanRename) {
        this.userCanRename = userCanRename;
    }

    public void setUserCanWrite(boolean userCanWrite) {
        this.userCanWrite = userCanWrite;
    }

    public void setUserCanNotWriteRelative(boolean userCanNotWriteRelative) {
        this.userCanNotWriteRelative = userCanNotWriteRelative;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserFriendlyName(String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
    }

    public void setIsAdminUser(boolean isAdminUser) {
        this.isAdminUser = isAdminUser;
    }

    public void setIsAnonymousUser(boolean isAnonymousUser) {
        this.isAnonymousUser = isAnonymousUser;
    }

    public void setEnableInsertRemoteImage(boolean enableInsertRemoteImage) {
        this.enableInsertRemoteImage = enableInsertRemoteImage;
    }

    public void setEnableRemoteLinkPicker(boolean enableRemoteLinkPicker) {
        this.enableRemoteLinkPicker = enableRemoteLinkPicker;
    }

    public void setEnableInsertRemoteFile(boolean enableInsertRemoteFile) {
        this.enableInsertRemoteFile = enableInsertRemoteFile;
    }

    public void setEnableRemoteAIContent(boolean enableRemoteAIContent) {
        this.enableRemoteAIContent = enableRemoteAIContent;
    }

    public void setDisableInsertLocalImage(boolean disableInsertLocalImage) {
        this.disableInsertLocalImage = disableInsertLocalImage;
    }

    public void setHidePrintOption(boolean hidePrintOption) {
        this.hidePrintOption = hidePrintOption;
    }

    public void setHideSaveOption(boolean hideSaveOption) {
        this.hideSaveOption = hideSaveOption;
    }

    public void setHideExportOption(boolean hideExportOption) {
        this.hideExportOption = hideExportOption;
    }

    public void setDisableExport(boolean disableExport) {
        this.disableExport = disableExport;
    }

    public void setDisableCopy(boolean disableCopy) {
        this.disableCopy = disableCopy;
    }

    public void setDisableInactiveMessages(boolean disableInactiveMessages) {
        this.disableInactiveMessages = disableInactiveMessages;
    }

    public void setUserCanOnlyComment(boolean userCanOnlyComment) {
        this.userCanOnlyComment = userCanOnlyComment;
    }

    public void setUserCanOnlyManageRedlines(boolean userCanOnlyManageRedlines) {
        this.userCanOnlyManageRedlines = userCanOnlyManageRedlines;
    }

    public void setDownloadAsPostMessage(boolean downloadAsPostMessage) {
        this.downloadAsPostMessage = downloadAsPostMessage;
    }

    public void setSaveAsPostmessage(boolean saveAsPostmessage) {
        this.saveAsPostmessage = saveAsPostmessage;
    }

    public void setEnableOwnerTermination(boolean enableOwnerTermination) {
        this.enableOwnerTermination = enableOwnerTermination;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.ownerId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MetaFile other = (MetaFile) obj;
        return Objects.equals(this.ownerId, other.ownerId);
    }
    
//    @Override
//    public String toString() {
//        return "MetaFile{ ownerId="
//                .concat(this.ownerId)
//                .concat("}");
//    }

}
