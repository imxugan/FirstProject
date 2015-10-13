package com.example.gitdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.gitdemo.base.BaseActivity;
import com.example.gitdemo.bean.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ParseDataActivity extends BaseActivity{

	private InputStream inputStream;
	private BufferedReader br;
	private String nodeName;
	private String attributeValue;
	private Person p;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_parsedata);
	}
	
	public void manage(View v){
		switch (v.getId()) {
		case R.id.btn_xml_parse:
			parseXmlDataFromAssets();
			break;
		case R.id.btn_sax:
			parseXmlDataBySax();
			break;
		case R.id.btn_json:
			parseJsonDataFromAssets();
			break;
		case R.id.btn_gson:
			parseJsonDataByGson();
			break;
		default:
			break;
		}
	}
	
	private void parseJsonDataByGson() {
		String json = getJsonData();
		parseJsonWithGson(json);
	}

	private void parseJsonWithGson(String json) {
		Gson gson = new Gson();
		//Person p = gson.fromJson(json, Person.class);
		List<Person> peoples = gson.fromJson(json, new TypeToken<List<Person>>() {}.getType());
		for (Person person : peoples) {
			Log.e("ii", "采用gson方式解析-----"+person.id+"---"+person.name+"---"+person.age);
		}
	}

	private void parseJsonDataFromAssets() {
		String json = getJsonData();
//		Log.i("ii", "json="+json);
		parseJsonWithJsonObject(json);
	}

	private void parseJsonWithJsonObject(String json) {
		try {
			JSONArray array = new JSONArray(json);
			Log.i("ii", "array.length()="+array.length());
			JSONObject jsonObject = null;
			String id ;
			String name;
			String age ;
			for (int i = 0; i < array.length(); i++) {
				jsonObject = array.getJSONObject(i);
				id = (String) jsonObject.get("id");
				name = (String) jsonObject.get("name");
				age = jsonObject.getString("age");
				Log.e("ii", id+"---"+name+"---"+age);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	private String getJsonData() {
		try {
			InputStream stream = getAssets().open("person_json.txt");//不要忘记了后缀.txt 否则会报错 InputStream stream = getAssets().open("person_json");
			BufferedReader re = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = re.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseXmlDataBySax() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			MyDefaultHandler handler = new MyDefaultHandler();
			// 将ContentHandler的实例设置到XMLReader中
			xmlReader.setContentHandler(handler);
			// 开始执行解析
			String xmlData = getXmlData();
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class MyDefaultHandler extends DefaultHandler{

		private StringBuilder name;
		private StringBuilder sex;
		private StringBuilder age;
		private String nodeName;
		private List<Person> personss;
		private Person pe;

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			Log.e("ii", "characters="+pe);
//			Log.i("ii", "characters------nodeName="+nodeName);
			if("name".equals(nodeName)){
				name.append(ch,start,length);
				pe.name = name.toString();
			}else if("age".equals(nodeName)){
				age.append(ch,start,length);
				if(!TextUtils.isEmpty(age) && TextUtils.isDigitsOnly(age)){
					pe.age = Integer.parseInt(age.toString());
				}
			}else if("sex".equals(nodeName)){
				Log.i("ii", "pe="+pe);
				sex.append(ch,start,length);
				if(!TextUtils.isEmpty(sex)){
					pe.sex = sex.toString();
				}
			}
		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			
			Log.e("ii", "startDocument=");
			name = new StringBuilder();
			sex = new StringBuilder();
			age = new StringBuilder();
			personss = new ArrayList<Person>();
			
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			nodeName = localName;
//			Log.e("ii", qName+"----startElement="+localName);
			Log.w("ii", "----startElement="+localName);
			if("person".equals(localName)){
				pe = new Person();
				
				String id = attributes.getValue("id");
				pe.id = id;
				personss.add(pe);
			}
		}
		
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
			Log.e("ii", "endDocument=");
			for (Person person : personss) {
				Log.e("ii", person.id+"---"+person.name+"---"+person.age+"---"+person.sex);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			Log.e("ii", "endElement  localName="+localName);
			if("person".equals(localName)){
				name.setLength(0);
				sex.setLength(0);
				age.setLength(0);
			}
		
		}
		
	}
	
	private String getXmlData(){
		AssetManager assetManager = getAssets();
		try {
			inputStream = assetManager.open("person.xml");
			br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void parseXmlDataFromAssets() {
		String xmlData = getXmlData();
		parseXmlByPull(xmlData);
		
	}

	/**
	 * 采用pull方式解析xml
	 * @param string
	 */
	private void parseXmlByPull(String string) {
		List<Person> persons = new ArrayList<Person>();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(string));
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				nodeName = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
//					Log.i("ii", "nodeName="+nodeName);
					
					if("person".equals(nodeName)){
						p = new Person();
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							attributeValue = parser.getAttributeValue(i);
							p.id = attributeValue;
						}
//						Log.i("ii", "p.id="+attributeValue);
					}else if(null != p){
						if("sex".equals(nodeName)){
							p.sex = parser.nextText();
//							Log.i("ii", "p.sex="+p.sex);
						}else if("age".equals(nodeName)){
							String nextText = parser.nextText();
							if(!TextUtils.isEmpty(nextText)&&TextUtils.isDigitsOnly(nextText)){
								p.age = Integer.parseInt(nextText);
//								Log.i("ii", "p.age="+p.age);
								
							}

						}else if("name".equals(nodeName)){
							p.name = parser.nextText();
//							Log.i("ii", "p.name="+p.name);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if("person".equals(nodeName) && p != null){
						persons.add(p);
						p = null;
					}
					break;
				default:
					break;
				}
				
				eventType = parser.next();
			}
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Person person : persons) {
			Log.e("ii", person.id+"---"+person.name+"---"+person.age+"---"+person.sex);
		}
		
	}

}
