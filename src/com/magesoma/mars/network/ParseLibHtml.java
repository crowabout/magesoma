package com.magesoma.mars.network;
import com.magesoma.mars.bean.BaseBean;
import com.magesoma.mars.bean.ReaderInfoBean;
import com.magesoma.mars.services.LibSxServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Administrator on 2016/7/21.
 */
public class ParseLibHtml {

    public static final String TAG_A_ATTR_HREF = "href";
    private List<ReaderInfoBean> sourceHolder;

    public ParseLibHtml() {
        sourceHolder = new ArrayList<ReaderInfoBean>();
    }


    /**
     * parse body of table in the html
     * @param tableBody html body
     * @return
     */
    public List<ReaderInfoBean> parserTagTable(String tableBody) {
        Document doc = Jsoup.parse(tableBody);
        //css selector .
        Elements eles = doc.select("table#contentTable tbody tr");
        sourceHolder.clear();
        Iterator<Element> iterators = eles.iterator();
       //跳过第一行的 文字部分
        iterators.next();
        while (iterators.hasNext()) {
            Element item = iterators.next();
//            parseTagTr(item);
//            ReaderInfoBean readerInfoBean = parseTagTrToReaderBean(item);
            ReaderInfoBean readerInfoBean = parseTagTr(item);
            sourceHolder.add(readerInfoBean);
        }
        return sourceHolder;
    }

    /**
     * parser the body of <tr></tr> ,Stripping the text  from the <td></td>
     *
     * @return
     */
    private ReaderInfoBean parseTagTr(Element element) {
        Elements eles = element.select("tr td");
        StringBuilder builder =new StringBuilder();
        int size = eles.size();
        for (int i = 0; i < size; i++) {
//            Element childEle = eles.get(i);
//            int childSize = childEle.children().size();
//            if (childSize > 0) {
//                if (childEle.children().hasAttr(TAG_A_ATTR_HREF)) {
//                    // get the  value of href attribute in <td><a href="/opac/book/2001184260;jessoin=xxxxxxxxxxxxxxxxxxxxxxxxxxx"></a></td>
//                    String attrValue = childEle.children().attr(TAG_A_ATTR_HREF);
//                    String innerText = childEle.text();
//                    System.out.println(innerText + "  " + attrValue);
//                }
//            } else {
                String text = eles.get(i).text();
             builder.append(text).append("$$$");
//                System.out.println(text);
//            }
        }
        Elements childele =eles.select("td a");
        if(childele.hasAttr(TAG_A_ATTR_HREF)){
           builder.append(childele.attr(TAG_A_ATTR_HREF));
        }

        return convertStr2Bean(builder.toString());
    }


    /***
     * 解析tr 标签中的文字,打印出来
     * @param element
     * @return
     */
    private ReaderInfoBean parseTagTrToReaderBean(Element element) {
        Elements eles = element.select("tr");
        String contents = eles.text();
        Elements childeles = eles.select("td a");
        if (childeles.hasAttr(TAG_A_ATTR_HREF)) {
            String attrValue = childeles.attr(TAG_A_ATTR_HREF);
            if (attrValue.contains(";"))
                attrValue = attrValue.substring(0, attrValue.indexOf(";"));
            contents += " " + attrValue;
        }

        return convertStr2Bean(contents);
    }

    private ReaderInfoBean convertStr2Bean(String strs) {

        if (LibSxClientEx.isEmpty(strs)) {
            throw new NullPointerException("要解析的数据不能 为空");
        }

        String[] info = strs.split("\\$\\$\\$", -1);
        if (info.length < 5) {
            throw new IndexOutOfBoundsException("解析出来的数据不够");
        }
        ReaderInfoBean bean = new ReaderInfoBean();
        bean.setCardNum(info[0]);
        bean.setCodeNum(info[1]);
        bean.setBookName(info[2]);
        bean.setBorrowTime(str2Date(info[3]));
        bean.setBackTime((str2Date(info[4])));
        bean.setBookCode(info[5]);
        return bean;
    }


    private Date str2Date(String str) {

        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        sdf.applyPattern("yyyy-MM-dd");
        Date date=null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public  int parseTagSpanForPage(String doc){
        /**
         *
         */
        String pageNum="";
       Elements ele =Jsoup.parse(doc).select("span#page_div > div.meneame > span.disabled");
        int size =ele.size();
        if(size>=2){
             pageNum =ele.get(1).text();
        }
       return filterToNumber(pageNum);
    }


    /**
     * 把字符的页数表示,变成数字
     * @param str
     * @return
     */
     private int filterToNumber(String str){

        if(LibSxClientEx.isEmpty(str)){
           return 0;
        }

        //[\\u4e00-\\u9fa5]*\\d*[,]*\\d+[\\u4e00-\\u9fa5]* 匹配整个str

        Pattern p =Pattern.compile("\\d*[,]*\\d+");
        Matcher m=p.matcher(str);

        String number="";
        if(m.find()){
            number=m.group();
        }
        if(number.contains(",")){
            number=number.replace(",","");
        }

        p.compile("\\d+");
        m=p.matcher(number);
        if(m.matches()){
            return Integer.parseInt(number);
        }
        return 0;
    }


//    public static void main(String[] args) {
////        try {
////
////            LibSxServiceImpl service = new LibSxServiceImpl();
////            String doc = new LibSxClientEx.Builder().build().post(LibSxClientEx.URL_LIBSX, "21");
////            ParseLibHtml parser = new ParseLibHtml();
////            parser.parserTagTable(doc);
//////             parser.parseTagSpanForPage(doc);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    String str="03100000291596$$$03101000472343$$$大败局：关于中国企业失败的MBA式教案$$$2016-06-14$$$2016-07-15$$$/opac/book/2000528327";
//        String temp[] =str.split("\\$\\$\\$",-1);
//
//
//    }

}
