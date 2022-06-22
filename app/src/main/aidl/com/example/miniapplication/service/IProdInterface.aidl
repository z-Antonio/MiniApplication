// IProdInterface.aidl
package com.example.miniapplication.service;

import com.example.miniapplication.service.Msg;
import com.example.miniapplication.service.Result;

// Declare any non-default types here with import statements

interface IProdInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    Result action(in Msg msg);
}