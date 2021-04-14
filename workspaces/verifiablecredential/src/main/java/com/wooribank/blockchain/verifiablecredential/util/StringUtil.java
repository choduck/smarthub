package com.wooribank.blockchain.verifiablecredential.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @FileName  	: StringUtil.java
 * @Description : 문자열 관련  함수 
 */

public class StringUtil {

	private static Runtime runtime = Runtime.getRuntime();
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**
     * 조회나, 수정form에 뿌려 줄때 값이 null이면 화면에 null이라고 찍히는 것을 없애기 위해사용한다
     * @param	obj		대상 오브젝트
     * @return	String	Null이 아닌 문자열
    */
    public static String NVL(Object obj){
        if(obj != null && !"null".equals(obj) && !"".equals(obj)) 
        	return obj.toString().trim();
        else
        	return "";
    }

    
    /**
     * 값이 null이면 화면에 default값 리턴
     * @param	obj		대상 오브젝트
     * @param	value	Null일 경우 대치할 문자열
     * @return	String	Null이 아닌 문자열
    */
    public static String NVL(Object obj,String value){
        if(obj != null  && !"null".equals(obj) && !"".equals(obj)) 
        	return obj.toString();
        else 
        	return value;
    }

    /**
     * 값이 null이면 화면에 default값 리턴
     * @param	obj		대상 오브젝트
     * @param	value	Null일 경우 대치할 int타입의 정수값
     * @return	int		Null이 아닌 정수값
    */
    public static int NVL(Object obj,int value){
        if(obj != null && !"null".equals(obj) && !"".equals(obj)) {
            try{
                return Integer.parseInt(obj.toString());
            }catch(NumberFormatException e){
                return 0;
            }
        }
        else 
        	return value;
    }

    /**
     * 값이 null이면 화면에 default값 리턴
     * @param	obj		대상 오브젝트
     * @param	value	Null일 경우 대치할 double타입의 실수값
     * @return	double	Null이 아닌 실수값
    */
    public static double NVL(Object obj, double value){
        if(obj != null && !"null".equals(obj) && !"".equals(obj)) {
            try{
                return Double.parseDouble(obj.toString());
            }catch(NumberFormatException e){
                return 0;
            }
        }
        else 
        	return value;
    }
    
    /**
     * 조회나, 수정form에 뿌려 줄때 값이 0이면  default값 리턴
     * @param	obj		대상 오브젝트
     * @param	value	대체할 default 값
     * @return	String	Null이 아닌 문자열
    */
    public static String replaceZero(Object obj, String value){
        if(obj != null && !"null".equals(obj) && !"".equals(obj) && "0".equals(obj)) 
        	return obj.toString().trim();
        else 
        	return value;
    }
    
    /** 문자열 추가시의 방향(왼쪽) */
    public static final int CHAR_POSITION_LEFT	= 1; 
    /** 문자열 추가시의 방향(오른쪽) */
    public static final int CHAR_POSITION_RIGHT	= 2; 
    
    /**
     * 입력문자열 좌/우 에 원하는 문자를 입력갯수 만큼 추가한후 반환한다.
     * @param str		원본데이터
     * @param character	추가될문자
     * @param pos		좌(1)/우(2)
     * @param point		갯수
     * @return String
     * @throws Exception
     */
    public static String makeChar( String str , String character , int pos , int point ) {

		int len = str.getBytes().length;
	    if( len >= point ) return str;
	   
	    byte bytes[] = new byte[ point ];
	    if( pos == CHAR_POSITION_RIGHT ) {
	    	for( int i = 0 ; i < bytes.length ; i++ ) {
	    		if( len > i ) bytes[ i ] = str.getBytes()[ i ];
	    		else  {
	    			bytes[ i ] = character.getBytes()[ 0 ];
	    		}
	    	}
	    }else if( pos == CHAR_POSITION_LEFT ) {
	    	for( int i = 0 ; i < bytes.length ; i++ ) {
	    		if( ( bytes.length - len ) > i ) {
	    			bytes[ i ] = character.getBytes()[ 0 ];
	    		}else {
	    			bytes[ i ] = str.getBytes()[ i - ( bytes.length - len ) ];
	    		}
	    	}
	    }
	    return new String( bytes , 0 , bytes.length );
	
    }
    
    /**
     * 입력문자열 좌/우 에 원하는 문자를 입력갯수 만큼 추가한후 반환한다.
     * @param num		원본데이터
     * @param character	추가될문자
     * @param pos		좌(1)/우(2)
     * @param point		갯수
     * @return String
     * @throws Exception
     */
    public static String makeChar( int num , String character , int pos , int point ) {	
    	return makeChar( String.valueOf( num ) , character , pos , point );
    }
    
    /**
	 * 한글 문자를 2바이트 기준으로 자른다.
	 * 짤린문자열의 마지막 문자가 한글이면 포함되지 않는다.
	 * db컬럼이  2군데 이상이어서  나누어서 넣을때 필요함
	 *  ex) 
	 * String a = "a한글b몇자cd일e?";
	 *	Iterator iter = byteSubstring(a, 3).listIterator();
	 *	while (iter.hasNext()) {
	 *		System.out.println(iter.next());
	 *	}
	 * 결과는 : a한, 글b, 몇, 자c, d일, e?

	 * @param data  원본 문자열
	 * @param offset  짜를 기준 수
	 * @return offset으로 짤린 수만큼 들어있는 어래이리스트
	 */
	public static ArrayList byteSubstring(String data, int offset) {

		ArrayList al = new ArrayList();
		
		if(data == null || data.equals(""))return al;

		String b = data;
		int totalDataLen = data.getBytes().length;
		int offset2 = offset;
		int strIndex = 0;
		int i = 0;
		

		// 첫글자가 한글일때 offset == 1 이면 의미가 없음
		if (offset == 1)
			return al;

		while (true) {

			String c = "";

			if (totalDataLen < offset) {
				c = new String(b.getBytes(), strIndex, totalDataLen);
				offset2 = totalDataLen;
			} else {
				c = new String(b.getBytes(), strIndex, offset);
				offset2 = offset;
				
			}
			
			
			offset2 =c.getBytes().length;
			//if (Character.getType(c.charAt(c.length() - 1)) == Character.OTHER_SYMBOL)
			//	offset2--;

			if (offset2 == 0)
				break;

			al.add(new String(c.getBytes(), 0, offset2));
			

			strIndex += offset2;
			totalDataLen -= offset2;
			++i;

			if (totalDataLen == 0)
				break;
		}

		return al;

	}    
	
	
	/**
	 * 문자열을 입력된 바이트 만큼 잘라서 반환한다.
	 * 만약 마지막 문자열이 한글 1byte 만 리턴될경우 이문자열은 포함되지 않는다.
	 * @param data 원본문자열
	 * @param bytes 짜를 byte length
	 * @return String
	 */
	public static String byteCut( String data , int bytes ) {
		if( data == null || data.length() == 0 ) return data;
		char c;
		int data_total_length_ = 0; 
		StringBuffer buffer = new StringBuffer();
		for( int i = 0 ; i < data.length() ; i++ ) {
			c = data.charAt( i );
			data_total_length_ += String.valueOf( c ).getBytes().length;
			if( bytes < data_total_length_ ) break;
			buffer.append( c );
		}
		return buffer.toString();
	}
	
	/**
	 * 입력된 문자열을 구분자를 기준으로 잘라서 배열을 만들어 리턴.
	 * StringTokenizer/split 과는 달리 구분자 사이의 값이 없더라도 모두 배열로 만들어 리턴한다.
	 * @param str 원본문자열
	 * @param div 구분자
	 * @return Object[]
	 * @throws ProFrameException
	 */
	public static Object[] toArray( String str , String div ) {
		Vector 	vector 	= null;
		int 		index	= -1;
		
		if( str == null || div == null ) return null;
		vector = new Vector();
		while( true ) {
			index = str.indexOf( div );
			if( index != -1 ) {
				vector.add( str.substring( 0 , index ) );
				str = str.substring( index + div.length() );
			}else {
				vector.add( str );
				break;
			}
		}
		return vector.toArray();
		
	}
	
	/**
	 * 반각문자로 변경한다
	 * @param	origin	변경할 값
	 * @return	String	변경된 값
	*/
	public static String toHalfChar( String origin ) {
	    char incode;
	    char target[] = new char[ origin.length() ];
	    for ( int i = 0;i < origin.length() ; i++ ) {
	       incode = origin.charAt(i);
	       int tmpcod1 = incode & 0xff00;
	       int tmpcod2 = incode & 0x00ff;
	       int tmpcod3 = incode & 0xffff;
	       if ( tmpcod3 ==  0x3000 ) {
	          target[ i ] =  0x20;  //전각스페이스를 반각으로...
	       } else if ( tmpcod1 ==  0xff00 ) {
	          //전각영문 또는 숫자를 반각 영문또는 숫자로..
	          //한글은 그대로...
	          target[ i ] =  (char)( tmpcod2 + 0x20 );
	       } else target[ i ] = (char)incode;
	    }
	    return (new String( target ));
	}
	   
	/**
	 * 전각문자로 변경한다.
	 * @param	src		변경할 값
	 * @return	String	변경된 값
	*/
	public static String toFullChar( String src ) {
	    if ( src == null ) return null;
	    // 변환된 문자들을 쌓아놓을 StringBuffer 를 마련한다
	    StringBuffer strBuf = new StringBuffer();
	    char c = 0;
	    int nSrcLength = src.length();
	    for( int i = 0 ; i < nSrcLength ; i++ ) {
	        c = src.charAt( i );
	        //영문이거나 특수 문자 일경우.
	        if (c >= 0x21 && c <= 0x7e) {  //공백일경우
	            c += 0xfee0;
	        }else if( c == 0x20 ) {
	            c = 0x3000;
	        }
	        // 문자열 버퍼에 변환된 문자를 쌓는다
	        strBuf.append( c );
	    }
	    return strBuf.toString();
	}

    public static final int MASK_JUMIN	= 1; 
    public static final int MASK_ACCOUNT	= 2; 
    public static final int MASK_CARD	= 3; 
	
	
	public static String toMask( int maskType , String data ) {
		if( "".equals( NVL( data ) ) ) return "";
		String retValue = "";
		if( maskType == MASK_JUMIN ) {
			retValue = data.substring( 0 , 6 ) + "*******";
		}else if( maskType == MASK_ACCOUNT ) {
			int index = data.length() - 4;
			retValue = data.substring( 0 , index ) + "****";
		}else if( maskType == MASK_CARD ) {
			retValue= data.substring( 0 , 6 ) + "****" + data.substring( 10 ); 
		}
		return retValue;
	}
    
	/**
	 * 지정된 길이만큼 문자열의 끝을 반환한다.
	 * @param	offset 시작 index
	 * @param	str		대상 문자열
	 * @return	String
	*/
    public static String tailer(int offset, String str) {
        if(str == null) str = "";
        if (str.length() <= offset) return "";

        return str.substring(offset);
    }
    
    
    /**
     * 문자열을 숫자로 변환
     * @param no
     * @return
     */
    public static int parseInt(String no) {
        int i = 0;
        if(no != null && !no.trim().equals("")){
            i = Integer.parseInt(no);
        }
        return i;
    }
 
    
    /**
     * 
     * @param no
     * @return long
     */
    public static long parseLong(String no){
        long l = 0;
        if(no != null && !no.trim().equals("")){
            l = Long.parseLong(no);
        }
        return l;
    }
    
    /**
     * @comment String 숫자를 포멧에 맞게 리턴한다.
     * @param	value			String 숫자 값
     * @param 	formatString	포맷 스트링
     * @return 	String			포멧에 맛게 변환된 문자열
     */
    public static String stringToMoneyFormat(String value, String formatString) {
    	
        DecimalFormat format = new DecimalFormat(formatString);
        
        if(value.split("\\-").length==1 && value.split("\\.").length==1){			//양수일때
        	return format.format(Long.parseLong(value));
       }else if(value.split("\\-").length==2 ){												//음수일때를 가리기 위해 
		   if(value.split("\\-")[0].equals("")){													//숫자 사이에 "-" 들어갈 경우가 아닌 경우에
			   return format.format(Long.parseLong(value));
		   }else{
			   return value;
		   }
       		 
       }else{
    	   return value;
       }

    }
    
    
    /**
     * @comment int 값을 숫자를 포멧에 맞게 리턴한다.
     * @param	value			int값
     * @param 	formatString	포맷 스트링
     * @return 	String			포멧에 맛게 변환된 문자열
     */
    public static String intToMoneyFormat(int value, String formatString) {
    	
        DecimalFormat format = new DecimalFormat(formatString);
        
        return format.format(value);

    }

    /**
     * @comment long 값을 숫자를 포멧에 맞게 리턴한다.
     * @param	value			long 값
     * @param 	formatString	포맷 스트링
     * @return 	String			포멧에 맛게 변환된 문자열
     */
    public static String longToMoneyFormat(long value, String formatString) {
    	
        DecimalFormat moneyFormat = new DecimalFormat(formatString);
        
        return moneyFormat.format(value);
    }
    
    /**
     * @comment double 값을 숫자를 포멧에 맞게 리턴한다.
     * @param	value			double 값
     * @param 	formatString	포맷 스트링
     * @return 	String			포멧에 맛게 변환된 문자열
     */
    public static String doubleToMoneyFormat(double value, String formatString) {
    	
        DecimalFormat moneyFormat = new DecimalFormat(formatString);
        
        return moneyFormat.format(value);
    }
    
    /**
     * @comment String 인코딩 UTF-8 변환 
     * @return 	String		포멧에 맛게 변환된 문자열
     */
    public static  String ko(String str){
        if(str==null || "".equals(str)) return "";
        try {
            return new String(str.getBytes("8859_1"),"UTF-8");
        } catch(Exception e) {
            return "";
        }
    } 
    
    /**
     *  문자열 특수문자변
     * @param str
     * @return
     */
    public static String replaceTag(String str) {
        //str = checkNull(str);
        StringBuffer result = new StringBuffer();
        String[] replace = new String[6];
        replace[0] = "<";
        replace[1] = ">";
        replace[2] = "'";
        replace[3] = "\"";
        replace[4] = "(";
        replace[5] = ")";
        String a = "";
        for(int i = 0; i < str.length() ; i++){
            a = str.substring(i,i+1);
            for(int j = 0 ; j < replace.length ; j++ ){
                if(a.equals(replace[j])){
                    switch(j){
                        case 0: a="&lt;";   break;
                        case 1: a="&gt;";   break;
                        case 2: a="&#39;";   break;
                        case 3: a="&quot;";   break;
                        case 4: a="&#40;";   break;
                        case 5: a="&#41;";   break;
                    }
                }
            }
            result.append( a );
        }
        str = result.toString();
        return str;
    }
    
	
	/**
	* @Method Name  : isNull
	* @Description 	: 빈값인지 확인
	* 
	* @param str
	* @return
	 */
    public static boolean isNull(String str) {
        return (str == null || "".equals(str.trim()));
    }
    
    /**
    * @Method Name  : nbsp2Empty
    * @Description 	: &nbsp;를 빈값으로 변환
    * 
    * @param field
    * @return
    */
    public static String nbsp2Empty(String str) {
      if ("&nbsp;".equals(str.trim())) return "";
      else return str;
    }
   
}
