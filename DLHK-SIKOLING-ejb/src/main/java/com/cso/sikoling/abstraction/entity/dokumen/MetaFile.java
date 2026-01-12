package com.cso.sikoling.abstraction.entity.dokumen;

import java.io.Serializable;
import java.util.Objects;


public class MetaFile implements Serializable {
    
    private final String baseFileName;
    private final boolean disablePrint;
    private final String ownerId;
    private final String postMessageOrigin;    
    private final long size;
    private final boolean supportsRename;
    private final String templateSource; 
    private final boolean userCanRename;
    private final boolean userCanNotWriteRelative;
    private final String userId;
    private final String userFriendlyName;
    private final boolean isAdminUser;
    private final boolean isAnonymousUser;
    private final boolean enableInsertRemoteImage;
    private final boolean enableRemoteLinkPicker;
    private final boolean enableInsertRemoteFile;
    private final boolean enableRemoteAIContent;
    private final boolean disableInsertLocalImage;
    private final boolean hidePrintOption;
    private final boolean hideSaveOption;
    private final boolean hideExportOption;
    private final boolean disableExport;
    private final boolean disableCopy;
    private final boolean disableInactiveMessages;
    private final boolean userCanOnlyComment;
    private final boolean userCanOnlyManageRedlines;
    private final boolean downloadAsPostMessage;
    private final boolean saveAsPostmessage;
    private final boolean enableOwnerTermination;   

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
            boolean saveAsPostmessage, boolean enableOwnerTermination) {
        this.baseFileName = baseFileName;
        this.disablePrint = disablePrint;
        this.ownerId = ownerId;
        this.postMessageOrigin = postMessageOrigin;
        this.size = size;
        this.supportsRename = supportsRename;
        this.templateSource = templateSource;
        this.userCanRename = userCanRename;
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
