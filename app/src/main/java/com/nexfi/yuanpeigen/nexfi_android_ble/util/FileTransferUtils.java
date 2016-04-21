package com.nexfi.yuanpeigen.nexfi_android_ble.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.ChatMessageAdapater;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gengbaolong on 2016/3/31.
 */
public class FileTransferUtils {
    public static final int REQUEST_CODE_SELECT_FILE = 2;
    public static final int REQUEST_CODE_LOCAL_IMAGE = 1;
    static ChatMessageAdapater mListViewAdapater;
    public static final int SELECT_A_PICTURE=3;//4.4以下
    public static final int SELECET_A_PICTURE_AFTER_KIKAT=4;//4.4以上
    /**
     * 分段发送文件
     *
     * @param path
     */
//    public static void sendFenDuanFile(final String path,final ChatActivity mContext,final String username,final int myAvatar,final String toIp,final String localIP,final int dynamicClientPort,final ListView lv,final List<ChatMessage> mDataArrays) {
//
//
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//
//                String fileName;
//                String fileSize;
//                Socket s = null;
//                OutputStream out = null;
//
//                String select_file_path = "";//发送端选择的文件的路径

//                final File fileToSend = new File(path);
//                fileSize = fileToSend.length()+"";
//                fileName = fileToSend.getName();
//                String extensionName = FileUtils.getExtensionName(fileName);
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.isPb = 1;//让进度条显示
//                if (fileName.length() > 23) {
//                    fileName = fileName.substring(0, 23) + "\n" + fileName.substring(23);
//                }
//
//                //设置文件图标
//                FileUtils.setFileIcon(chatMessage, extensionName);
//                //文件名
//                byte[] file = new byte[256];//定义字节数组用于存储文件名字大小
//                byte[] tfile = fileToSend.getName().getBytes();
//                for (int i = 0; i < tfile.length; i++) {
//                    file[i] = tfile[i];
//                }
//                file[tfile.length] = 0;
//                try {
//                    out.write(file, 0, file.length);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                //文件本身大小
//                byte[] size = new byte[64];
//                byte[] tsize = ("" + fileToSend.length()).getBytes();
//
//                for (int i = 0; i < tsize.length; i++) {
//                    size[i] = tsize[i];
//                }
//
//                size[tsize.length] = 0;
//                try {
//                    out.write(size, 0, size.length);//灏嗘枃浠跺ぇ灏忎紶鍒版帴鏀剁
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                //读取文件的输入流
//                FileInputStream fis = null;
//                byte[] buf = new byte[1024 * 1024];
//                try {
//                    fis = new FileInputStream(fileToSend);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                int readsize = 0;
//                //TODO
//                chatMessage.fromAvatar = myAvatar;
//                chatMessage.msgType = 2;
//                chatMessage.toIP = toIp;
//                chatMessage.fromIP = localIP;
//                chatMessage.sendTime = FileUtils.getDateNow();
//                chatMessage.fromNick = username;
//                chatMessage.type = "chatP2P";
//                //TODO 2016/3/25 9:50
//                chatMessage.chat_id = toIp;
//                mDataArrays.add(chatMessage);
//                //TODO
//                mListViewAdapater = new ChatMessageAdapater(mContext, mDataArrays);
//
//                //发送开始就显示
//                mContext.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        lv.setAdapter(mListViewAdapater);
//                        if (mListViewAdapater != null) {
//                            mListViewAdapater.notifyDataSetChanged();
//                        }
//                        if (mDataArrays.size() > 0) {
//                            lv.setSelection(lv.getCount() - 1);
//                        }
//                    }
//                });
//                try {
//                    while ((readsize = fis.read(buf, 0, buf.length)) > 0) {
//                        out.write(buf, 0, readsize);
//                        //等待一会
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        out.flush();
//                    }
//                    out.close();
//                    s.close();
//                    //TODO
//                    //隐藏进度条
//                    chatMessage.isPb = 0;
//                    mListViewAdapater = new ChatMessageAdapater(mContext, mDataArrays);
//                    //发送完毕就隐藏
//                    mContext.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            lv.setAdapter(mListViewAdapater);
//                            if (mListViewAdapater != null) {
//                                mListViewAdapater.notifyDataSetChanged();
//                            }
//                            if (mDataArrays.size() > 0) {
//                                lv.setSelection(lv.getCount() - 1);
//                            }
//                        }
//                    });
//                    BuddyDao buddyDao = new BuddyDao(mContext);
//                    buddyDao.addP2PMsg(chatMessage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//
//    //开启文件接收端
//    public static void startServer(final int dynamicServerPort,final ChatActivity context,final String toIp,final int avatar,final ListView lv,final List<ChatMessage> mDataArrays,final ChatMessageAdapater mListViewAdapater){
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                ServerSocket serversock = null;  //监听端口
//                try {
//                    serversock = new ServerSocket(dynamicServerPort);
//                    while (true) {
//                        Socket sock = serversock.accept();            //循环等待客户端连接
//                        new Thread(new TcpFenDuanThread(sock,context,toIp,avatar,lv,mDataArrays,mListViewAdapater)).start(); //当成功连接客户端后开启新线程接收文件
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }


    /**
     * 选择本地文件
     */
    public static void selectFileFromLocal(Activity context) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        context.startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * 从图库获取图片
     */
    public static void selectPicFromLocal(Activity context) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            context.startActivityForResult(intent, SELECT_A_PICTURE);
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent = new Intent(Intent.ACTION_PICK);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
            context.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
        }
//        context.startActivityForResult(intent, REQUEST_CODE_LOCAL_IMAGE);
    }


    /**
     * @param
     * @param bytes
     * @param
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes) {
        if (bytes != null) {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;//只读边,不读内容
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,newOpts);

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float hh = 100f;//
            float ww = 100f;//
            int be = 1;
            if (w > h && w > ww) {
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;//设置采样率

            newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                    newOpts);
            return bitmap;
        }
        return null;
    }


    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
//        float hh = 800f;//
//        float ww = 480f;//
        float hh = 100f;//
        float ww = 100f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(byte[] bytes,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
    }


    public static Bitmap zoomBitmap(String path, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        /*
         * 官方文档这样写着“If set to true, the decoder will return null (no bitmap),
         * but the out... fields will still be set, allowing the caller to query
         * the bitmap without having to allocate the memory for its
         * pixels.　”，大意就是说inJustDecodeBounds
         * 为true时，不给出实际的Bitmap对象，但你可以得到这张图片的实际信息，这样你就可以计算缩放 比例了
         */
        options.inJustDecodeBounds = true; // 下面的bitmap暂时为null

        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bitmap为空,不占用内存的
        int multiple = (int) (options.outHeight / 54); // 计算缩放值
        if (multiple <= 0) // 如果缩放值小于0，则不对图片进行缩放
            multiple = 1;

        options.inSampleSize = multiple;
        options.inJustDecodeBounds = false; // 得到缩放后的Bitmap对象
        bitmap = BitmapFactory.decodeFile(path, options); //
        return bitmap;
    }



    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }





    /**
     * 把一个文件转化为字节
     * @param file
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);//
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE) //当文件的长度超过了int的最大值
            {
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                return null;
            }
            is.close();
        }
        return bytes;
    }




    /**
     * 文件转化为字节数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }



    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFile
     * @return
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

}
