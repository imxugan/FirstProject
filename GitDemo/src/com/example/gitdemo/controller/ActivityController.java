package com.example.gitdemo.controller;

import java.util.LinkedList;

import android.app.Activity;

public class ActivityController {
	public static LinkedList<Activity> activityMap = new LinkedList<Activity>();
	
	public static void addActivity(Activity a){
		activityMap.add(a);
	}
	
	public static void removeActivity(Activity a){
		activityMap.remove(a);
	}
	
	public static void finishAll(){
		for (Activity activity : activityMap) {
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
