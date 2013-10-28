/*     */ package ui;
/*     */ 
/*     */ import conf.LODifierProperties;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
/*     */ import pipeline.Pipeline;
/*     */ 
/*     */ public class UserInterface
/*     */ {
/*  28 */   private static String rdf_output_file = null;
/*  29 */   private static String lemmatizer_output = null;
/*  30 */   private static String cc_input = "";
/*  31 */   private static String boxer_path = null;
/*  32 */   private static String ukb_path = null;
/*  33 */   private static String ukb_output = null;
/*  34 */   private static String cc_output = "";
/*  35 */   private static String boxer_output = "";
/*  36 */   private static Properties config = new LODifierProperties();
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 103 */     if (args.length < 2) {
/* 104 */       System.out.println("Usage: $ java -jar LODifier.jar <input file> <output file>\ne.g. '$ java -jar LODifier.jar input.txt output.txt'");
/*     */     } else {
/* 106 */       String cc_in = args[0];
/* 107 */       String rdf_out = args[1];
/*     */ 
/* 109 */       byte[] buffer = new byte[(int)new File(cc_in).length()];
/* 110 */       BufferedInputStream f = new BufferedInputStream(new FileInputStream(cc_in));
/* 111 */       f.read(buffer);
/* 112 */       setCC_input(new String(buffer));
/* 113 */       setRdf_output_file(rdf_out);
/* 114 */       setBoxer_path(config.getProperty("boxer.path"));
/* 115 */       setCC_output(config.getProperty("parser.out"));
/* 116 */       setLemmatizer_output(config.getProperty("lemmatizer.out"));
/* 117 */       setUKB_path(config.getProperty("ukb.path"));
/* 118 */       setUKB_output(config.getProperty("ukb.out"));
/* 119 */       setBoxer_output(config.getProperty("boxer.out"));
/*     */ 
/* 121 */       Pipeline pipeline = new Pipeline();
/* 122 */       pipeline.startPipeline();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setRdf_output_file(String p_rdf_output_file)
/*     */   {
/* 134 */     rdf_output_file = p_rdf_output_file;
/*     */   }
/*     */ 
/*     */   public static String getRdf_output_file()
/*     */   {
/* 142 */     return rdf_output_file;
/*     */   }
/*     */ 
/*     */   public static void setLemmatizer_output(String p_lemmatizer_output_file)
/*     */   {
/* 150 */     lemmatizer_output = p_lemmatizer_output_file;
/*     */   }
/*     */ 
/*     */   public static String getLemmatizer_output()
/*     */   {
/* 158 */     return lemmatizer_output;
/*     */   }
/*     */ 
/*     */   public static void setUKB_output(String p_ukb_output_file)
/*     */   {
/* 167 */     ukb_output = p_ukb_output_file;
/*     */   }
/*     */ 
/*     */   public static String getUKB_output()
/*     */   {
/* 175 */     return ukb_output;
/*     */   }
/*     */ 
/*     */   public static void setUKB_path(String p_ukb_path)
/*     */   {
/* 183 */     ukb_path = p_ukb_path;
/*     */   }
/*     */ 
/*     */   public static String getUKB_path()
/*     */   {
/* 191 */     return ukb_path;
/*     */   }
/*     */ 
/*     */   public static void setBoxer_path(String boxer_path)
/*     */   {
/* 199 */     boxer_path = boxer_path;
/*     */   }
/*     */ 
/*     */   public static String getBoxer_path()
/*     */   {
/* 207 */     return boxer_path;
/*     */   }
/*     */ 
/*     */   public static String getCC_input()
/*     */   {
/* 215 */     return cc_input;
/*     */   }
/*     */ 
/*     */   public static void setCC_input(String p_cc_input)
/*     */   {
/* 223 */     cc_input = p_cc_input;
/*     */   }
/*     */ 
/*     */   public static void setCC_output(String cc_output)
/*     */   {
/* 231 */     cc_output = cc_output;
/*     */   }
/*     */ 
/*     */   public static String getCC_output()
/*     */   {
/* 239 */     return cc_output;
/*     */   }
/*     */ 
/*     */   public static void setBoxer_output(String boxer_output)
/*     */   {
/* 247 */     boxer_output = boxer_output;
/*     */   }
/*     */ 
/*     */   public static String getBoxer_output()
/*     */   {
/* 255 */     return boxer_output;
/*     */   }
/*     */ 
/*     */   public static Properties getConfig()
/*     */   {
/* 263 */     return config;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     ui.UserInterface
 * JD-Core Version:    0.6.2
 */