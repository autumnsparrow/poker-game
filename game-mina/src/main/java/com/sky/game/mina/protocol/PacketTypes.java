package com.sky.game.mina.protocol;

public enum PacketTypes {
	AsyncPacketType(0,"Ice Server write to MinaServer"),
	SyncPacketType(1,"Hall&Platform Server Packet Type"),
	DeviceBindingPacketType(2,"Device Binding Packet Type"),
	PingPacketType(3,"Ping Packet Type"),
	InvalidPacketType(4,"Invlaid Packet Type");
	
	private int type;
	private String description;
	private PacketTypes(int type,String description){
		this.type=type;
		this.description=description;
	}
	private PacketTypes(int type) {
		this.type = type;
	}
	
	
	public boolean compare(PacketTypes types){
		return this.type==types.type;
		
	}
	
	public static PacketTypes getInstance(int type){
		PacketTypes packetTypes=InvalidPacketType;
		switch (type) {
		case 1:
			packetTypes=SyncPacketType;
			break;
		case 2:
			packetTypes=DeviceBindingPacketType;
			break;
		case 3:
			packetTypes=PingPacketType;
			break;
			
		default:
			packetTypes=InvalidPacketType;
			break;
		}
		return packetTypes;
	}
	
	public int getType(){
		return this.type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PacketTypes:[type=" +
				type +
				",description=" +
				description +
				"]";
	}
	public String getDescription() {
		return description;
	}
	
	
	
}
