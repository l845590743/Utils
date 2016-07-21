 private String getJsonStringFromGZIP(HttpResponse response) {  
        String jsonString = null;  
        try {  
            InputStream is = response.getEntity().getContent();  
            BufferedInputStream bis = new BufferedInputStream(is);  
            bis.mark(2);  
            // 取前两个字节  
            byte[] header = new byte[2];  
            int result = bis.read(header);  
            // reset输入流到开始位置  
            bis.reset();  
            // 判断是否是GZIP格式  
            int headerData = getShort(header);  
            // Gzip 流 的前两个字节是 0x1f8b  
            if (result != -1 && headerData == 0x1f8b) { LogUtil.d("HttpTask", " use GZIPInputStream  ");  
                is = new GZIPInputStream(bis);  
            } else {  
                LogUtil.d("HttpTask", " not use GZIPInputStream");  
                is = bis;  
            }  
            InputStreamReader reader = new InputStreamReader(is, "utf-8");  
            char[] data = new char[100];  
            int readSize;  
            StringBuffer sb = new StringBuffer();  
            while ((readSize = reader.read(data)) > 0) {  
                sb.append(data, 0, readSize);  
            }  
            jsonString = sb.toString();  
            bis.close();  
            reader.close();  
        } catch (Exception e) {  
            LogUtil.e("HttpTask", e.toString(),e);  
        }  
   
        LogUtil.d("HttpTask", "getJsonStringFromGZIP net output : " + jsonString );  
        return jsonString;  
    }  
   
    private int getShort(byte[] data) {  
        return (int)((data[0]<<8) | data[1]&0xFF);  
    }  