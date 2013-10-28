/*     */ package ner;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ 
/*     */ public class Wikifier
/*     */ {
/*     */   public static String wikify(String text)
/*     */   {
/*  34 */     String html = "";
/*     */ 
/*  36 */     StringBuffer sb = new StringBuffer();
/*     */     try
/*     */     {
/*  41 */       URL u = new URL("http://wdm.cs.waikato.ac.nz:8080/services/wikify?source=" + URLEncoder.encode(text, "utf-8") + "&sourceMode=HTML&minProbability=0.5");
/*  42 */       URLConnection conn = u.openConnection();
/*  43 */       BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       String inputLine;
/*  44 */       while ((inputLine = in.readLine()) != null)
/*     */       {
/*     */         String inputLine;
/*  45 */         sb.append(inputLine);
/*     */       }
/*  47 */       in.close();
/*  48 */       return sb.toString();
/*     */     }
/*     */     catch (MalformedURLException e)
/*     */     {
/*  53 */       e.printStackTrace();
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  56 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/*  59 */       e.printStackTrace();
/*  60 */       String splittext1 = "";
/*  61 */       String splittext2 = "";
/*  62 */       int splitindex = text.length() / 2;
/*  63 */       for (int i = splitindex; i <= splitindex; i--) {
/*  64 */         String splitstring = text.charAt(i) + text.charAt(i + 1);
/*  65 */         if (splitstring.equals(" ,")) {
/*  66 */           splittext1 = text.substring(0, i);
/*  67 */           splittext2 = text.substring(i, text.length() - 1);
/*  68 */           break;
/*     */         }
/*     */       }
/*     */       try {
/*  72 */         URL u = new URL("http://wdm.cs.waikato.ac.nz:8080/services/wikify?source=" + URLEncoder.encode(text, "utf-8") + "&sourceMode=HTML&minProbability=0.5");
/*  73 */         URLConnection conn = u.openConnection();
/*  74 */         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */         String inputLine;
/*  75 */         while ((inputLine = in.readLine()) != null)
/*     */         {
/*     */           String inputLine;
/*  76 */           sb.append(inputLine);
/*     */         }
/*  78 */         in.close();
/*  79 */         String html1 = sb.toString();
/*  80 */         u = new URL("http://wdm.cs.waikato.ac.nz:8080/services/wikify?source=" + URLEncoder.encode(text, "utf-8") + "&sourceMode=HTML&minProbability=0.5");
/*  81 */         conn = u.openConnection();
/*  82 */         in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*  83 */         while ((inputLine = in.readLine()) != null) {
/*  84 */           sb.append(inputLine);
/*     */         }
/*  86 */         in.close();
/*  87 */         String html2 = sb.toString();
/*  88 */         html = html1 + html2;
/*     */       }
/*     */       catch (MalformedURLException e1)
/*     */       {
/*  92 */         e1.printStackTrace();
/*     */       }
/*     */       catch (UnsupportedEncodingException e1) {
/*  95 */         e1.printStackTrace();
/*     */       }
/*     */       catch (IOException f) {
/*  98 */         f.printStackTrace();
/*     */       }
/*     */     }
/* 101 */     return html;
/*     */   }
/*     */ 
/*     */   public static String wikify(String text, float prob)
/*     */     throws IOException
/*     */   {
/* 112 */     URL u = new URL("http://aifb-ls3-calc.aifb.uni-karlsruhe.de:8080/wpmservlet-en/web/service?task=wikify&wrapInXml=false&minProbability=" + prob + "&source=" + URLEncoder.encode(text, "utf-8"));
/* 113 */     URLConnection conn = u.openConnection();
/* 114 */     BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/* 115 */     StringBuffer sb = new StringBuffer();
/*     */     String inputLine;
/* 117 */     while ((inputLine = in.readLine()) != null)
/*     */     {
/*     */       String inputLine;
/* 118 */       sb.append(inputLine);
/*     */     }
/* 120 */     in.close();
/* 121 */     String html = sb.toString();
/* 122 */     return html;
/*     */   }
/*     */ 
/*     */   public static HashMap getWikipediaLinks(String wikified_text)
/*     */   {
/* 132 */     HashMap entity = new HashMap();
/*     */ 
/* 134 */     Pattern pattern = Pattern.compile("<a href=\"http://www.en.wikipedia.org/wiki/(.*?)\" class=.*?>(.*?)</a>");
/* 135 */     String wikiuri = "http://dbpedia.org/resource/";
/* 136 */     StringBuffer sb = new StringBuffer();
/* 137 */     Matcher m = pattern.matcher(wikified_text);
/*     */ 
/* 139 */     while (m.find()) {
/* 140 */       String wiki = m.group(2);
/* 141 */       String text = m.group(1);
/* 142 */       text = WordUtils.capitalize(text);
/* 143 */       wiki = wiki.replace(' ', '_');
/* 144 */       text = text.replace(' ', '_');
/*     */ 
/* 146 */       entity.put(wiki, "dbpedia:" + text);
/*     */     }
/* 148 */     return entity;
/*     */   }
/*     */ 
/*     */   public static HashMap getWikipediaLinks2(String wikified_text)
/*     */   {
/* 158 */     HashMap entity = new HashMap();
/* 159 */     Pattern pattern = Pattern.compile("\\[\\[.*?\\]\\]");
/* 160 */     String wikiuri = "http://dbpedia.org/resource/";
/* 161 */     StringBuffer sb = new StringBuffer();
/* 162 */     Matcher m = pattern.matcher(wikified_text);
/*     */ 
/* 164 */     while (m.find()) {
/* 165 */       String concept = m.group();
/*     */ 
/* 167 */       if (concept.contains("|")) {
/* 168 */         String text = concept.substring(2, concept.indexOf("|"));
/* 169 */         text = WordUtils.capitalize(text);
/* 170 */         text = text.replace(' ', '_');
/* 171 */         String wiki = concept.substring(concept.indexOf("|") + 1, concept.length() - 2);
/* 172 */         wiki = wiki.replace(' ', '_');
/* 173 */         entity.put(wiki, "<a href=\"" + wikiuri + text + "\">" + "dbpedia:" + text + "</a>");
/*     */       }
/*     */       else {
/* 176 */         String wiki = concept.substring(2, concept.indexOf("]"));
/* 177 */         String text = wiki;
/* 178 */         text = WordUtils.capitalize(text);
/* 179 */         wiki = wiki.replace(' ', '_');
/* 180 */         text = text.replace(' ', '_');
/* 181 */         entity.put(wiki, "<a href=\"" + wikiuri + text + "\">" + "dbpedia:" + text + "</a>");
/*     */       }
/*     */     }
/*     */ 
/* 185 */     return entity;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     ner.Wikifier
 * JD-Core Version:    0.6.2
 */