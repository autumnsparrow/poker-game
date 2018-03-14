// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager;

import com.sky.game.manager.Filt;
import com.sky.game.manager.model.security.ChannelPrincipalAssignment;
import java.util.List;

privileged aspect Filt_Roo_Service {
    
    public abstract long Filt.countAllChannelPrincipalAssignments();    
    public abstract void Filt.deleteChannelPrincipalAssignment(ChannelPrincipalAssignment channelPrincipalAssignment);    
    public abstract ChannelPrincipalAssignment Filt.findChannelPrincipalAssignment(Long id);    
    public abstract List<ChannelPrincipalAssignment> Filt.findAllChannelPrincipalAssignments();    
    public abstract List<ChannelPrincipalAssignment> Filt.findChannelPrincipalAssignmentEntries(int firstResult, int maxResults);    
    public abstract void Filt.saveChannelPrincipalAssignment(ChannelPrincipalAssignment channelPrincipalAssignment);    
    public abstract ChannelPrincipalAssignment Filt.updateChannelPrincipalAssignment(ChannelPrincipalAssignment channelPrincipalAssignment);    
}