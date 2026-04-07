package com.mcs.app

object MCSConstants {
    const val ROOT_PATH = "MCSProjects"
    const val WORKSPACE_FILE = "workspace.json"
    const val PROPS_PATH = ".mcs"
    const val EDITOR_PROPS_PATH = "$PROPS_PATH/.editor"
    const val EDITOR_PROPS_FILE = "$EDITOR_PROPS_PATH/open_files.json"
    const val EDITOR_PROPS_FILE_BAK = "$EDITOR_PROPS_FILE.bak"
    const val MOBILECODESPACE_HOME = "MobileCodeSpace"

    private const val BASE_URL = "https://raw.githubusercontent.com/Xed-Editor/Karbon-PackagesX/main"

    const val PROOT_ARM = "$BASE_URL/arm/proot"
    const val PROOT_ARM64 = "$BASE_URL/aarch64/proot"
    const val PROOT_X64 = "$BASE_URL/x86_64/proot"

    const val TALLOC_ARM = "$BASE_URL/arm/libtalloc.so.2"
    const val TALLOC_ARM64 = "$BASE_URL/aarch64/libtalloc.so.2"
    const val TALLOC_X64 = "$BASE_URL/x86_64/libtalloc.so.2"

    private const val ROOTFS_BASE = "https://github.com/Xed-Editor/Karbon-PackagesX/releases/download/ubuntu"

    const val ROOTFS_ARM = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-armhf.tar.gz"
    const val ROOTFS_ARM64 = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-arm64.tar.gz"
    const val ROOTFS_X64 = "$ROOTFS_BASE/ubuntu-base-24.04.3-base-amd64.tar.gz"
}
