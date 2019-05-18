package br.com.victorcatao.amaki.utils.helpers

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import br.com.victorcatao.amaki.R
import org.jetbrains.anko.selector
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UpdateMediaHelper : CameraIntentHelper.OnCameraResultListener, MediaGalleryPickerHelper.OnImageResultListener {

    private var mGalleryPickerHelper: MediaGalleryPickerHelper? = null
    private var mCameraIntentHelper: CameraIntentHelper? = null
    private var mContext: Activity? = null

    private var mOnRequestReady: OnRequestReady? = null
    private var mIsWithRemoveOptions = false
    private var fromCamera: Boolean = false
    private var mOnlyImg: Boolean = false
    private var mOnlyVideo: Boolean = false


    constructor(context: Activity, onRequestReady: OnRequestReady, onlyImg: Boolean) {
        mCameraIntentHelper = CameraIntentHelper(context, this, onlyImg)
        mGalleryPickerHelper = MediaGalleryPickerHelper(context, this, onlyImg)
        mOnRequestReady = onRequestReady
        mContext = context
        mOnlyImg = onlyImg
        mOnlyVideo = false
    }

    constructor(context: Activity, onRequestReady: OnRequestReady, onlyImg: Boolean, onlyVideo: Boolean) {
        mCameraIntentHelper = CameraIntentHelper(context, this, onlyImg)
        mGalleryPickerHelper = MediaGalleryPickerHelper(context, this, onlyImg)
        mOnRequestReady = onRequestReady
        mContext = context
        mOnlyImg = onlyImg
        mOnlyVideo = onlyVideo
    }

    constructor(context: Fragment, onRequestReady: OnRequestReady, onlyImg: Boolean) {
        mCameraIntentHelper = CameraIntentHelper(context, this, onlyImg)
        mGalleryPickerHelper = MediaGalleryPickerHelper(context, this, onlyImg)
        mOnRequestReady = onRequestReady
        mContext = context.activity
    }

    override fun onMediaReady(file: File, isImage: Boolean) {
        val out: FileOutputStream? = null
        try {
            //            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            //            Matrix matrix = PhotoUtil.rotateImageBasedOnExifData(file.getPath());
            //            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            //            File outFile = new File(file.getAbsolutePath());

            //            out = new FileOutputStream(outFile, false);
            //            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            //             PNG is a lossless format, the compression factor (100) is ignored
            mOnRequestReady!!.onMediaReady(file, fromCamera, isImage)
        } catch (e: Exception) {
            mOnRequestReady!!.onMediaFailed(ERROR_UNKNOWN, "Unkown Error while saving image")
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                mOnRequestReady!!.onMediaFailed(ERROR_UNKNOWN, "Unkown Error while saving image")
                e.printStackTrace()
            }

        }

    }


    override fun onMediaFailed(code: Int, msg: String) {
        mOnRequestReady!!.onMediaFailed(code, msg)
    }

    fun showRemoveOption(show: Boolean) {
        mIsWithRemoveOptions = show
    }

    fun initiate() {
        val arrayOptions: Array<String>

        when {
            mIsWithRemoveOptions -> arrayOptions = mContext!!.resources.getStringArray(R.array.update_image_options)
            mOnlyImg -> arrayOptions = mContext!!.resources.getStringArray(R.array.upload_image_options)
            mOnlyVideo -> arrayOptions = mContext!!.resources.getStringArray(R.array.upload_video_options)
            else -> arrayOptions = mContext!!.resources.getStringArray(if (mOnlyImg) R.array.upload_image_options else R.array.upload_media_options)
        }

        mContext?.selector(mContext?.getString(R.string.dialog_title_select_an_option), arrayOptions.asList(), { _, which ->
            when {
                arrayOptions[which] == (mContext?.getString(R.string.update_media_camera)) -> {
                    fromCamera = true
                    dispatchTakePictureIntent()
                }
                arrayOptions[which] == (mContext?.getString(R.string.update_media_image_album)) -> {
                    fromCamera = false
                    dispatchPhotoSelectionIntent()
                }
                arrayOptions[which] == mContext?.getString(R.string.update_media_video_album) -> dispatchVideoSelectionIntent()
                arrayOptions[which] == (mContext?.getString(R.string.update_media_remove)) -> mOnRequestReady?.onMediaReady(null, fromCamera, true)
            }
        })


        //        DialogHelper.createListDialog(mContext, mContext.getString(R.string.dialog_title_select_an_option),
        //                arrayOptions,
        //                new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialog, int which) {
        //                        if (arrayOptions[which].equals(mContext.getString(R.string.update_media_camera))) {
        //                            fromCamera = true;
        //                            dispatchTakePictureIntent();
        //                        } else if (arrayOptions[which].equals(mContext.getString(R.string.update_media_image_album))) {
        //                            fromCamera = false;
        //                            dispatchPhotoSelectionIntent();
        //                        } else if (arrayOptions[which].equals(mContext.getString(R.string.update_media_video_album))) {
        //                            dispatchVideoSelectionIntent();
        //                        } else if (arrayOptions[which].equals(mContext.getString(R.string.update_media_remove))) {
        //                            mOnRequestReady.onMediaReady(null, fromCamera, true);
        //                        }
        //                    }
        //                }).show();
    }


    /**
     * Opens Gallery to select image.
     */
    private fun dispatchPhotoSelectionIntent() {
        mGalleryPickerHelper!!.startGalleryPicker(MediaGalleryPickerHelper.Type.IMAGE)
    }

    private fun dispatchVideoSelectionIntent() {
        mGalleryPickerHelper!!.startGalleryPicker(MediaGalleryPickerHelper.Type.VIDEO)
    }


    /**
     * Opens system camera application to capture a photo.
     */
    private fun dispatchTakePictureIntent() {
        mCameraIntentHelper!!.startCamera()
    }

    fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (permsRequestCode) {
            MediaGalleryPickerHelper.REQUEST_IMAGE_SELECTOR -> mGalleryPickerHelper!!.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
            CameraIntentHelper.REQUEST_TAKE_PICTURE -> mCameraIntentHelper!!.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            MediaGalleryPickerHelper.REQUEST_IMAGE_SELECTOR -> mGalleryPickerHelper!!.onActivityResult(requestCode, resultCode, data)
            CameraIntentHelper.REQUEST_TAKE_PICTURE -> mCameraIntentHelper!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    interface OnRequestReady {
        fun onMediaReady(file: File?, fromCamera: Boolean, isImage: Boolean)

        fun onMediaFailed(code: Int, msg: String)
    }

    companion object {
        val ERROR_UNKNOWN = 3
    }
}
