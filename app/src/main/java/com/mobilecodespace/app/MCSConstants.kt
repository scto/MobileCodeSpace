package com.mobilecodespace.app

object MCSConstants {
    const val ROOT_PATH = "MCSProjects"
    const val WORKSPACE_FILE = "workspace.json"
    const val PROPS_PATH = ".mcs"
    const val EDITOR_PROPS_PATH = "$PROPS_PATH/.editor"
    const val EDITOR_PROPS_FILE = "$EDITOR_PROPS_PATH/open_files.json"
    const val EDITOR_PROPS_FILE_BAK = "$EDITOR_PROPS_FILE.bak"
    const val MOBILECODESPACE_HOME = "MobileCodeSpace"

    private const val BASE_URL = "https://raw.githubusercontent.com/scto/Karbon-PackagesX/main"

    const val PROOT_ARM = "$BASE_URL/arm/proot"
    const val PROOT_ARM64 = "$BASE_URL/aarch64/proot"
    const val PROOT_X64 = "$BASE_URL/x86_64/proot"

    const val TALLOC_ARM = "$BASE_URL/arm/libtalloc.so.2"
    const val TALLOC_ARM64 = "$BASE_URL/aarch64/libtalloc.so.2"
    const val TALLOC_X64 = "$BASE_URL/x86_64/libtalloc.so.2"

    private const val ROOTFS_BASE = "https://github.com/Xscto/Karbon-PackagesX/releases/download/ubuntu"

    const val ROOTFS_ARM = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-armhf.tar.gz"
    const val ROOTFS_ARM64 = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-arm64.tar.gz"
    const val ROOTFS_X64 = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-amd64.tar.gz"

    // URLs for Onboarding
    private const val ONBOARDING_BASE_URL = "https://github.com/scto/MobileCodeSpace-Packages/releases/download"
    const val CMDLINE_TOOLS_URL = "$ONBOARDING_BASE_URL/cmdline/cmdline.zip"
    const val SCRIPTS_URL = "$ONBOARDING_BASE_URL/scripts/scripts.zip"
    const val UBUNTU_ARM64_URL = "$ONBOARDING_BASE_URL/ubuntu/ubuntu-arm64.tar.gz"
    const val UBUNTU_ARMHF_URL = "$ONBOARDING_BASE_URL/ubuntu/ubuntu-armhf.tar.gz"
    const val BOOTSTRAP_AARCH64_URL = "$ONBOARDING_BASE_URL/bootstrap/bootstrap-aarch64.zip"
    const val BOOTSTRAP_ARM_URL = "$ONBOARDING_BASE_URL/bootstrap/bootstrap-arm.zip"
}
