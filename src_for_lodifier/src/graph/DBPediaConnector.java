/*     */ package graph;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class DBPediaConnector
/*     */ {
/*     */   public static String getDBPediaLinksFromFile(ArrayList<String> dbpediaLinks)
/*     */   {
/*  23 */     String dbpediaTriples = "";
/*  24 */     ArrayList<String> vars = new ArrayList();
/*  25 */     String var = "";
/*     */     String uri;
/*     */     try
/*     */     {
/*  28 */       FileReader fileReader = new FileReader("article_categories_en.nt");
/*  29 */       BufferedReader bufferedReader = new BufferedReader(fileReader);
/*  30 */       String line = null;
/*     */       Iterator localIterator;
/*  31 */       for (; (line = bufferedReader.readLine()) != null; 
/*  32 */         localIterator.hasNext()) { localIterator = dbpediaLinks.iterator(); continue; uri = (String)localIterator.next();
/*     */ 
/*  34 */         String[] urisplit = uri.split(":");
/*  35 */         if (line.startsWith("<http://dbpedia.org/resource/" + urisplit[1] + "> <http://purl.org/dc/terms/subject> ")) {
/*  36 */           Pattern pattern = Pattern.compile("(Category:.*)> \\.");
/*  37 */           Matcher matcher = pattern.matcher(line);
/*  38 */           while (matcher.find()) {
/*  39 */             var = matcher.group(1);
/*     */ 
/*  41 */             vars.add(uri + " dcterms:subject dbpedia:" + var + " .");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  46 */       bufferedReader.close();
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  49 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/*  52 */       e.printStackTrace();
/*     */     }
/*  54 */     StringBuilder sb = new StringBuilder();
/*  55 */     for (String s : vars) {
/*  56 */       sb.append(s);
/*  57 */       sb.append("\n");
/*     */     }
/*  59 */     String newDBpediaTriples = sb.toString();
/*  60 */     System.out.println(newDBpediaTriples);
/*  61 */     return newDBpediaTriples;
/*     */   }
/*     */ 
/*     */   public static String getDBPediaLinks(ArrayList<String> dbpediaLinks) {
/*  65 */     String dbpediaTriples = "";
/*  66 */     ArrayList vars = new ArrayList();
/*  67 */     String var = "";
/*     */     String[] urisplit;
/*  68 */     for (String uri : dbpediaLinks)
/*     */     {
/*  70 */       urisplit = uri.split(":");
/*     */       try
/*     */       {
/*  73 */         URL u = new URL("http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX+dbpedia%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fresource%2F%3E%0D%0APREFIX+dcterms%3A+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0D%0ASELECT+DISTINCT+%3Fcategory%0D%0AWHERE+{%0D%0Adbpedia%3A" + urisplit[1] + "+dcterms%3Asubject+%3Fcategory.%0D%0A++++}+%0D%0AORDER+BY+%3Fcategory&format=application%2Fjavascript&timeout=0&debug=on" + 
/*  74 */           URLEncoder.encode(uri, "utf-8"));
/*  75 */         URLConnection conn = u.openConnection();
/*  76 */         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*  77 */         StringBuffer sb = new StringBuffer();
/*     */         String inputLine;
/*  79 */         while ((inputLine = in.readLine()) != null)
/*     */         {
/*     */           String inputLine;
/*  80 */           sb.append(inputLine);
/*     */         }
/*  82 */         in.close();
/*  83 */         String html = sb.toString();
/*     */ 
/*  85 */         String[] htmlsplit = html.split("document.writeln");
/*  86 */         for (String line : htmlsplit) {
/*  87 */           Pattern pattern = Pattern.compile("<td>http://dbpedia.org/resource/Category:(.*)</td>");
/*  88 */           Matcher matcher = pattern.matcher(line);
/*  89 */           while (matcher.find()) {
/*  90 */             var = matcher.group(1);
/*     */ 
/*  92 */             vars.add(uri + " dcterms:subject dbpedia:" + var + " .");
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (MalformedURLException e) {
/*  97 */         e.printStackTrace();
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 100 */         e.printStackTrace();
/*     */       }
/*     */       catch (IOException e) {
/* 103 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 106 */     StringBuilder sb = new StringBuilder();
/* 107 */     for (String line : vars) {
/* 108 */       sb.append(line);
/* 109 */       sb.append("\n");
/*     */     }
/* 111 */     String newDBpediaTriples = sb.toString();
/* 112 */     System.out.println(newDBpediaTriples);
/* 113 */     return newDBpediaTriples;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     graph.DBPediaConnector
 * JD-Core Version:    0.6.2
 */