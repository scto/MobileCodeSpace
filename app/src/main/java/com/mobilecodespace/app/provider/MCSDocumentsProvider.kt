package com.mobilecodespace.app.provider

import android.provider.DocumentsProvider
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.database.Cursor

// Minimal implementation for now
class MCSDocumentsProvider : DocumentsProvider() {
    override fun onCreate(): Boolean = true
    override fun queryRoots(projection: Array<out String>?): Cursor? = null
    override fun queryDocument(documentId: String?, projection: Array<out String>?): Cursor? = null
    override fun queryChildDocuments(parentDocumentId: String?, projection: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun openDocument(documentId: String?, mode: String, signal: CancellationSignal?): ParcelFileDescriptor? = null
}
