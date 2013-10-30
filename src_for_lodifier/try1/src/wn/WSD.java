/*     */ package wn;
/*     */ 
/*     */ import io.DocumentReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class WSD
/*     */ {
/*     */   public int getSenseNr(String word, String wordsense, String word_type, String[] lines)
/*     */   {
/*  24 */     Integer word_type_cnt = Integer.valueOf(1);
/*  25 */     for (String line : lines) {
/*  26 */       if (line.startsWith(word + " ")) {
/*  27 */         Matcher matcher = Pattern.compile("(([0-9]+)-" + word_type + "):[0-9]").matcher(line);
/*  28 */         while (matcher.find()) {
/*  29 */           String sense = matcher.group(1);
/*  30 */           if (sense.matches(wordsense))
/*     */           {
/*     */             break;
/*     */           }
/*  34 */           word_type_cnt = Integer.valueOf(word_type_cnt.intValue() + 1);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  39 */     return word_type_cnt.intValue();
/*     */   }
/*     */ 
/*     */   public boolean isAdjectiveSatellite(String adjective_satellite, String adjective) throws IOException {
/*  43 */     boolean isadjectivesatellite = false;
/*  44 */     DocumentReader reader = new DocumentReader();
/*  45 */     String[] lines = reader.readFile("wordnet-wordsensesandwords.cropped.ttl");
/*  46 */     for (String line : lines) {
/*  47 */       if (line.contains(adjective_satellite)) {
/*  48 */         isadjectivesatellite = true;
/*  49 */         break;
/*     */       }
/*  51 */       if (line.contains(adjective)) {
/*  52 */         isadjectivesatellite = false;
/*  53 */         break;
/*     */       }
/*     */     }
/*  56 */     return isadjectivesatellite;
/*     */   }
/*     */ 
/*     */   public String addWordNetURIs(String rdf, String[] uris) {
/*  60 */     String[] rdfsplit = rdf.split("\n");
/*  61 */     for (String line : rdfsplit) {
/*  62 */       for (String uri : uris) {
/*  63 */         Map used = new HashMap();
/*  64 */         Integer uricnt = Integer.valueOf(0);
/*  65 */         Matcher matcher = Pattern.compile("((.*) (.*) )(.*:(.*)) \\.").matcher(line);
/*  66 */         while (matcher.find()) {
/*  67 */           if ((uri.contains(matcher.group(5))) && 
/*  68 */             (!used.containsKey(uricnt))) {
/*  69 */             String var = matcher.group(2);
/*  70 */             rdf = rdf.replace(line, matcher.group(1).concat(uri).concat(" ."));
/*  71 */             used.put(uricnt, uri);
/*     */           }
/*     */         }
/*     */ 
/*  75 */         uricnt = Integer.valueOf(uricnt.intValue() + 1);
/*     */       }
/*     */     }
/*  78 */     return rdf;
/*     */   }
/*     */ 
/*     */   public String addWordNetURIs2(String rdf, String[] uris) {
/*  82 */     String[] rdfsplit = rdf.split("\n");
/*  83 */     ArrayList used_lines = new ArrayList();
/*  84 */     for (String line : rdfsplit) {
/*  85 */       for (String uri : uris) {
/*  86 */         Map used = new HashMap();
/*  87 */         Integer uricnt = Integer.valueOf(0);
/*  88 */         Matcher matcher = Pattern.compile("((.*) (.*) )(.*:(.*)) \\.").matcher(line);
/*  89 */         while (matcher.find()) {
/*  90 */           if ((uri.contains(matcher.group(5))) && 
/*  91 */             (!used.containsKey(uricnt))) {
/*  92 */             String var = matcher.group(2);
/*  93 */             String[] uri_seperated = uri.split(":");
/*  94 */             rdf = rdf.replace(line, matcher.group(1).concat("<a href=\"http://purl.org/vocabularies/princeton/wn30/" + uri_seperated[1] + "\">" + uri + "</a>").concat(" ."));
/*  95 */             used.put(uricnt, uri);
/*     */           }
/*     */         }
/*     */ 
/*  99 */         uricnt = Integer.valueOf(uricnt.intValue() + 1);
/*     */       }
/*     */     }
/* 102 */     return rdf;
/*     */   }
/*     */ }

/* Location:           C:\Users\vomi\Dropbox\UIC\FALL_2013\CS_586\project\cs586_source_code\LODifier_2012-06-11\LODifier_2012-06-11\LODifier.jar
 * Qualified Name:     wn.WSD
 * JD-Core Version:    0.6.2
 */