/*    */ package org.eclipse.jdt.internal.jarinjarloader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class RsrcURLStreamHandler extends URLStreamHandler
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */ 
/*    */   public RsrcURLStreamHandler(ClassLoader classLoader)
/*    */   {
/* 34 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */   protected URLConnection openConnection(URL u) throws IOException {
/* 38 */     return new RsrcURLConnection(u, this.classLoader);
/*    */   }
/*    */ 
/*    */   protected void parseURL(URL url, String spec, int start, int limit)
/*    */   {
/*    */     String file;
/*    */     String file;
/* 43 */     if (spec.startsWith("rsrc:")) {
/* 44 */       file = spec.substring(5);
/*    */     }
/*    */     else
/*    */     {
/*    */       String file;
/* 45 */       if (url.getFile().equals("./")) {
/* 46 */         file = spec;
/*    */       }
/*    */       else
/*    */       {
/*    */         String file;
/* 47 */         if (url.getFile().endsWith("/"))
/* 48 */           file = url.getFile() + spec;
/*    */         else
/* 50 */           file = spec; 
/*    */       }
/*    */     }
/* 51 */     setURL(url, "rsrc", "", -1, null, null, file, null, null);
/*    */   }
/*    */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     org.eclipse.jdt.internal.jarinjarloader.RsrcURLStreamHandler
 * JD-Core Version:    0.6.2
 */