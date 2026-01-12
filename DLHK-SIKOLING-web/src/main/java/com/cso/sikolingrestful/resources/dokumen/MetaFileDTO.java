package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.MetaFile;
import java.util.Objects;

public class MetaFileDTO {
    private String baseFileName;
    private boolean disablePrint;
    private String ownerId;
    private String postMessageOrigin;    
    private long size;
    private boolean supportsRename;
    private String templateSource; 
    private boolean userCanRename;
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

    public MetaFileDTO() {
    }
    
    public MetaFileDTO(String baseFileName, String ownerId, long size, 
                                String userId, String userFriendlyName) {
        this.baseFileName = baseFileName;
        this.disablePrint = false;
        this.ownerId = ownerId;
        this.postMessageOrigin = null;
        this.size = size;
        this.supportsRename = false;
        this.templateSource = null;
        this.userCanRename = false;
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
    
    public MetaFileDTO(MetaFile t) {
        this.baseFileName = t.getBaseFileName();
        this.disablePrint = t.isDisablePrint();
        this.ownerId = t.getOwnerId();
        this.postMessageOrigin = t.getPostMessageOrigin();
        this.size = t.getSize();
        this.supportsRename = t.isSupportsRename();
        this.templateSource = t.getTemplateSource();
        this.userCanRename = t.isUserCanRename();
        this.userCanNotWriteRelative = t.isUserCanNotWriteRelative();
        this.userId = t.getUserId();
        this.userFriendlyName = t.getUserFriendlyName();
        this.isAdminUser = t.isIsAdminUser();
        this.isAnonymousUser = t.isIsAnonymousUser();
        this.enableInsertRemoteImage = t.isEnableInsertRemoteImage();
        this.enableRemoteLinkPicker = t.isEnableRemoteLinkPicker();
        this.enableInsertRemoteFile = t.isEnableInsertRemoteFile();
        this.enableRemoteAIContent = t.isEnableRemoteAIContent();
        this.disableInsertLocalImage = t.isDisableInsertLocalImage();
        this.hidePrintOption = t.isHidePrintOption();
        this.hideSaveOption = t.isHideSaveOption();
        this.hideExportOption = t.isHideExportOption();
        this.disableExport = t.isDisableExport();
        this.disableCopy = t.isDisableCopy();
        this.disableInactiveMessages = t.isDisableInactiveMessages();
        this.userCanOnlyComment = t.isUserCanOnlyComment();
        this.userCanOnlyManageRedlines = t.isUserCanOnlyManageRedlines();
        this.downloadAsPostMessage = t.isDownloadAsPostMessage();
        this.saveAsPostmessage = t.isSaveAsPostmessage();
        this.enableOwnerTermination = t.isEnableOwnerTermination();
    }

    public String getBaseFileName() {
        return baseFileName;
    }

    public void setBaseFileName(String baseFileName) {
        this.baseFileName = baseFileName;
    }

    public boolean isDisablePrint() {
        return disablePrint;
    }

    public void setDisablePrint(boolean disablePrint) {
        this.disablePrint = disablePrint;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPostMessageOrigin() {
        return postMessageOrigin;
    }

    public void setPostMessageOrigin(String postMessageOrigin) {
        this.postMessageOrigin = postMessageOrigin;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isSupportsRename() {
        return supportsRename;
    }

    public void setSupportsRename(boolean supportsRename) {
        this.supportsRename = supportsRename;
    }

    public String getTemplateSource() {
        return templateSource;
    }

    public void setTemplateSource(String templateSource) {
        this.templateSource = templateSource;
    }

    public boolean isUserCanRename() {
        return userCanRename;
    }

    public void setUserCanRename(boolean userCanRename) {
        this.userCanRename = userCanRename;
    }

    public boolean isUserCanNotWriteRelative() {
        return userCanNotWriteRelative;
    }

    public void setUserCanNotWriteRelative(boolean userCanNotWriteRelative) {
        this.userCanNotWriteRelative = userCanNotWriteRelative;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }

    public void setUserFriendlyName(String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
    }

    public boolean isIsAdminUser() {
        return isAdminUser;
    }

    public void setIsAdminUser(boolean isAdminUser) {
        this.isAdminUser = isAdminUser;
    }

    public boolean isIsAnonymousUser() {
        return isAnonymousUser;
    }

    public void setIsAnonymousUser(boolean isAnonymousUser) {
        this.isAnonymousUser = isAnonymousUser;
    }

    public boolean isEnableInsertRemoteImage() {
        return enableInsertRemoteImage;
    }

    public void setEnableInsertRemoteImage(boolean enableInsertRemoteImage) {
        this.enableInsertRemoteImage = enableInsertRemoteImage;
    }

    public boolean isEnableRemoteLinkPicker() {
        return enableRemoteLinkPicker;
    }

    public void setEnableRemoteLinkPicker(boolean enableRemoteLinkPicker) {
        this.enableRemoteLinkPicker = enableRemoteLinkPicker;
    }

    public boolean isEnableInsertRemoteFile() {
        return enableInsertRemoteFile;
    }

    public void setEnableInsertRemoteFile(boolean enableInsertRemoteFile) {
        this.enableInsertRemoteFile = enableInsertRemoteFile;
    }

    public boolean isEnableRemoteAIContent() {
        return enableRemoteAIContent;
    }

    public void setEnableRemoteAIContent(boolean enableRemoteAIContent) {
        this.enableRemoteAIContent = enableRemoteAIContent;
    }

    public boolean isDisableInsertLocalImage() {
        return disableInsertLocalImage;
    }

    public void setDisableInsertLocalImage(boolean disableInsertLocalImage) {
        this.disableInsertLocalImage = disableInsertLocalImage;
    }

    public boolean isHidePrintOption() {
        return hidePrintOption;
    }

    public void setHidePrintOption(boolean hidePrintOption) {
        this.hidePrintOption = hidePrintOption;
    }

    public boolean isHideSaveOption() {
        return hideSaveOption;
    }

    public void setHideSaveOption(boolean hideSaveOption) {
        this.hideSaveOption = hideSaveOption;
    }

    public boolean isHideExportOption() {
        return hideExportOption;
    }

    public void setHideExportOption(boolean hideExportOption) {
        this.hideExportOption = hideExportOption;
    }

    public boolean isDisableExport() {
        return disableExport;
    }

    public void setDisableExport(boolean disableExport) {
        this.disableExport = disableExport;
    }

    public boolean isDisableCopy() {
        return disableCopy;
    }

    public void setDisableCopy(boolean disableCopy) {
        this.disableCopy = disableCopy;
    }

    public boolean isDisableInactiveMessages() {
        return disableInactiveMessages;
    }

    public void setDisableInactiveMessages(boolean disableInactiveMessages) {
        this.disableInactiveMessages = disableInactiveMessages;
    }

    public boolean isUserCanOnlyComment() {
        return userCanOnlyComment;
    }

    public void setUserCanOnlyComment(boolean userCanOnlyComment) {
        this.userCanOnlyComment = userCanOnlyComment;
    }

    public boolean isUserCanOnlyManageRedlines() {
        return userCanOnlyManageRedlines;
    }

    public void setUserCanOnlyManageRedlines(boolean userCanOnlyManageRedlines) {
        this.userCanOnlyManageRedlines = userCanOnlyManageRedlines;
    }

    public boolean isDownloadAsPostMessage() {
        return downloadAsPostMessage;
    }

    public void setDownloadAsPostMessage(boolean downloadAsPostMessage) {
        this.downloadAsPostMessage = downloadAsPostMessage;
    }

    public boolean isSaveAsPostmessage() {
        return saveAsPostmessage;
    }

    public void setSaveAsPostmessage(boolean saveAsPostmessage) {
        this.saveAsPostmessage = saveAsPostmessage;
    }

    public boolean isEnableOwnerTermination() {
        return enableOwnerTermination;
    }

    public void setEnableOwnerTermination(boolean enableOwnerTermination) {
        this.enableOwnerTermination = enableOwnerTermination;
    }
    
    public MetaFile toMetaFile() {
        return new MetaFile(
                this.baseFileName, this.disablePrint, this.ownerId, 
                this.postMessageOrigin, this.size, this.supportsRename, 
                this.templateSource, this.userCanRename, this.userCanNotWriteRelative, 
                this.userId, this.userFriendlyName, this.isAdminUser, 
                this.isAnonymousUser, this.enableInsertRemoteImage, this.enableRemoteLinkPicker, 
                this.enableInsertRemoteFile, this.enableRemoteAIContent, this.disableInsertLocalImage, 
                this.hidePrintOption, this.hideSaveOption, this.hideExportOption, this.disableExport, 
                this.disableCopy, this.disableInactiveMessages, this.userCanOnlyComment, 
                this.userCanOnlyManageRedlines, this.downloadAsPostMessage, this.saveAsPostmessage, 
                this.enableOwnerTermination
            );
    
        }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 531 * hash + Objects.hashCode(this.ownerId);
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
        final MetaFileDTO other = (MetaFileDTO) obj;
        return Objects.equals(this.ownerId, other.ownerId);
    }
    
    @Override
    public String toString() {
        return "MetaFileDTO{ ownerId="
                .concat(this.ownerId)
                .concat("}");
    }
}
