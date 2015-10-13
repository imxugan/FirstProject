package com.example.gitdemo.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Book implements Parcelable{
	public String id;
	public int pages;
	public String name;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeInt(pages);
		dest.writeString(name);
	}
	
	 // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：  
    // android.os.BadParcelableException:  
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person  
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用  
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；  
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    // 5.反序列化对象 
	//这里必须写成	CREATOR
	public static Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
		
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
		
		@Override
		public Book createFromParcel(Parcel source) {
			Book book = new Book();
			//这里要注意，写入和读出的顺序要一致
			book.id = source.readString();
			book.pages = source.readInt();
			book.name = source.readString();
			return book;
		}
	};
	

}
