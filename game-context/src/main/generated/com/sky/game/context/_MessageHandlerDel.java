// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.0
//
// <auto-generated>
//
// Generated from file `message.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.sky.game.context;

public interface _MessageHandlerDel extends Ice._ObjectDel
{
    Message invoke(Message msg, java.util.Map<String, String> __ctx, Ice.Instrumentation.InvocationObserver __obsv)
        throws IceInternal.LocalExceptionWrapper,
               MessageException;
}
