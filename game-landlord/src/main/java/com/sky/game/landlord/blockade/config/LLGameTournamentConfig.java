/**
 * @sparrow
 * @Dec 27, 2014   @11:36:48 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.GameUtil;

/**
 * 
 * 
 * 
 * GameTournamentConfig:
 * 
 *   docroot/channel.index
 *           channel/
 *                	
 *           	
 *           	    
 * 
 * @author sparrow
 *
 */
public class LLGameTournamentConfig {
	
	int channelId;
	int ftType;
	String ftDescription;
	List<String> freeTournamentConfigs;
	
	
	int etType;
	String etDescription;
	List<String> etEliminationTournamentConfigs;
	
	int vipType;
	String vipDescription;
	List<String> vipEliminationTournamentConfigs;
	
	
	int btType;
	String btDescription;
	List<String> blockadeTournamentConfigs;
	@JsonIgnore
	List<FreeTournamentConfig> ft;
	@JsonIgnore
	List<EliminationTournamentConfig> et;
	@JsonIgnore
	List<EliminationTournamentConfig> vip;
	@JsonIgnore
	List<BlockadeTournamentConfig> bt;
	
	@JsonIgnore
	Map<Long,FreeTournamentConfig> ftMaps;
	@JsonIgnore
	Map<Long,EliminationTournamentConfig> etMaps;
	@JsonIgnore
	Map<Long,EliminationTournamentConfig> vipMaps;
	@JsonIgnore
	Map<Long,BlockadeTournamentConfig> btMaps;
	
	
	
	
	
	
	public static LLGameTournamentConfig obtain(int channelId){
		LLGameTournamentConfig o=new LLGameTournamentConfig();
		
		o.ftType=LLAreaTypes.FreeTournamentArea.type;
		o.ftDescription=LLAreaTypes.FreeTournamentArea.description;
		o.freeTournamentConfigs=GameUtil.getList();
		for(int i=0;i<3;i++){
			o.freeTournamentConfigs.add(String.valueOf(i*1000));
		}
		o.etType=LLAreaTypes.TournamentArea.type;
		o.etDescription=LLAreaTypes.TournamentArea.description;
		o.etEliminationTournamentConfigs=GameUtil.getList();
		
		for(int i=0;i<2;i++){
			o.etEliminationTournamentConfigs.add(String.valueOf(i*2000));
		}
		
		
		o.vipType=LLAreaTypes.VipTournamentArea.type;
		o.vipDescription=LLAreaTypes.VipTournamentArea.description;
		o.vipEliminationTournamentConfigs=GameUtil.getList();
		for(int i=0;i<5;i++){
			o.vipEliminationTournamentConfigs.add(String.valueOf(i*3000));
		}
		
		
		o.btType=LLAreaTypes.BlockadeTournamentArea.type;
		o.btDescription=LLAreaTypes.BlockadeTournamentArea.description;
		o.blockadeTournamentConfigs=GameUtil.getList();
		for(int i=0;i<2;i++){
			o.blockadeTournamentConfigs.add(String.valueOf(i*400));
		}
		
		
		o.channelId=channelId;
		return o;
		
	}
	
	/**
	 * 
	 */
	public LLGameTournamentConfig() {
		// TODO Auto-generated constructor stub
	}

	


	
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	
	private static InputStream getInputStream(String s) throws Exception{
		InputStream is=null;
		if(s.startsWith("http://")){
			try {
				URL u=new URL(s);
				is=u.openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			is=LLGameTournamentConfig.class.getResourceAsStream(s);
		}
		
		if(is==null){
			throw new Exception(" can't get the resource by url:"+s);
		}
		
		return is;
	}
	
	/**
	 * support online update the configuraiton.
	 * 
	 * @param type
	 * @param url
	 * @throws Exception 
	 */
	public void update(int type,String url) throws Exception{
		InputStream is=getInputStream(url);
		switch (type) {
		case LLAreaTypes.FREE_TOURNAMENT:
		{
			FreeTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, FreeTournamentConfig.class);
			ftMaps.put(obj.getId(), obj);
		}
			break;
		case LLAreaTypes.ELIMINATION_TOURNAMENT:
		{
			EliminationTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, EliminationTournamentConfig.class);
			etMaps.put(obj.getId(), obj);
		}
			break;
		case LLAreaTypes.VIP_ELIMINATION_TOURNAMENT:
		{
			EliminationTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, EliminationTournamentConfig.class);
			vipMaps.put(obj.getId(), obj);
			
		}
			break;
		case LLAreaTypes.BLOCKED_TOURNAMENT:
		{
			BlockadeTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, BlockadeTournamentConfig.class);
			btMaps.put(obj.getId(), obj);
		}
			break;

		default:
			break;
		}
	}
	
	/**
	 * Game load the configuration.
	 * @param url
	 * @throws Exception 
	 */
	public static LLGameTournamentConfig load(String url) throws Exception{
		 LLGameTournamentConfig o;
		InputStream is=getInputStream(url);
		o=GameContxtConfigurationLoader.loadXmlConfiguration(is, LLGameTournamentConfig.class);
		o.et=GameUtil.getList();
		for(String s:o.etEliminationTournamentConfigs){
			is=getInputStream(s);
			EliminationTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, EliminationTournamentConfig.class);
			
			o.et.add(obj);
		}
		
		o.ft=GameUtil.getList();
		for(String s:o.freeTournamentConfigs){
			is=getInputStream(s);
			FreeTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, FreeTournamentConfig.class);
			o.ft.add(obj);
		}
		
		
		o.vip=GameUtil.getList();
		for(String s:o.vipEliminationTournamentConfigs){
			is=getInputStream(s);
			EliminationTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, EliminationTournamentConfig.class);
			o.vip.add(obj);
		}
		
		o.bt=GameUtil.getList();
		for(String s:o.blockadeTournamentConfigs){
			is=getInputStream(s);
			BlockadeTournamentConfig obj=GameContxtConfigurationLoader.loadXmlConfiguration(is, BlockadeTournamentConfig.class);
			o.bt.add(obj);
		}
		
		o.btMaps=GameUtil.getListAsMap(o.bt);
		o.ftMaps=GameUtil.getListAsMap(o.ft);
		o.vipMaps=GameUtil.getListAsMap(o.vip);
		o.etMaps=GameUtil.getListAsMap(o.et);
		return o;
	}
	
	public static void main(String args[]){
//		LLGameTournamentConfig o=LLGameTournamentConfig.obtain(1);
//		String channelConfig=GameContextGlobals.getXmlJsonConvertor().format(o);
////		InputStream is=LLGameTournamentConfig.class.getResourceAsStream("/META-INF/tournament/channel-1.xml");
////		LLGameTournamentConfig o=GameContxtConfigurationLoader.loadXmlConfiguration(is, LLGameTournamentConfig.class);
////		String channelConfig=GameContextGlobals.getJsonConvertor().format(o);
//		System.out.println(channelConfig);
//		HashMap<String,LinkedList<String>> maps=new HashMap<String, LinkedList<String>>();
//		LinkedList<String> rooms=new LinkedList<String>();
//		for(int i=20000;i<20010;i++){
//			rooms.add(String.valueOf(i));
//		}
//		maps.put((LLAreaTypes.FreeTournamentArea.toString()), rooms);
//		maps.put((LLAreaTypes.TournamentArea.toString()), rooms);
//		String roomIndex=GameContextGlobals.getXmlJsonConvertor().format(maps);
//		InputStream is=LLGameTournamentConfig.class.getResourceAsStream("/META-INF/tournament/1/index.xml");
//		maps=GameContxtConfigurationLoader.loadXmlConfiguration(is, HashMap.class);
//		String roomIndex=GameContextGlobals.getXmlJsonConvertor().format(maps);
	//	System.out.println(roomIndex);
		
		FreeTournamentConfig o=FreeTournamentConfig.obtain();
		//BlockadeTournamentConfig o=BlockadeTournamentConfig.obtain();
		String channelConfig=GameContextGlobals.getXmlJsonConvertor().format(o);
		
		System.out.println(channelConfig);
		
	}

	public int getFtType() {
		return ftType;
	}

	public void setFtType(int ftType) {
		this.ftType = ftType;
	}

	public String getFtDescription() {
		return ftDescription;
	}

	public void setFtDescription(String ftDescription) {
		this.ftDescription = ftDescription;
	}

	

	public int getEtType() {
		return etType;
	}

	public void setEtType(int etType) {
		this.etType = etType;
	}

	public String getEtDescription() {
		return etDescription;
	}

	public void setEtDescription(String etDescription) {
		this.etDescription = etDescription;
	}

	
	public int getVipType() {
		return vipType;
	}

	public void setVipType(int vipType) {
		this.vipType = vipType;
	}

	public String getVipDescription() {
		return vipDescription;
	}

	public void setVipDescription(String vipDescription) {
		this.vipDescription = vipDescription;
	}


	public int getBtType() {
		return btType;
	}

	public void setBtType(int btType) {
		this.btType = btType;
	}

	public String getBtDescription() {
		return btDescription;
	}

	public void setBtDescription(String btDescription) {
		this.btDescription = btDescription;
	}

	public List<String> getFreeTournamentConfigs() {
		return freeTournamentConfigs;
	}

	public void setFreeTournamentConfigs(List<String> freeTournamentConfigs) {
		this.freeTournamentConfigs = freeTournamentConfigs;
	}

	public List<String> getEtEliminationTournamentConfigs() {
		return etEliminationTournamentConfigs;
	}

	public void setEtEliminationTournamentConfigs(
			List<String> etEliminationTournamentConfigs) {
		this.etEliminationTournamentConfigs = etEliminationTournamentConfigs;
	}

	public List<String> getVipEliminationTournamentConfigs() {
		return vipEliminationTournamentConfigs;
	}

	public void setVipEliminationTournamentConfigs(
			List<String> vipEliminationTournamentConfigs) {
		this.vipEliminationTournamentConfigs = vipEliminationTournamentConfigs;
	}

	public List<String> getBlockadeTournamentConfigs() {
		return blockadeTournamentConfigs;
	}

	public void setBlockadeTournamentConfigs(List<String> blockadeTournamentConfigs) {
		this.blockadeTournamentConfigs = blockadeTournamentConfigs;
	}

	public List<FreeTournamentConfig> getFt() {
		return ft;
	}

	public void setFt(List<FreeTournamentConfig> ft) {
		this.ft = ft;
	}

	public List<EliminationTournamentConfig> getEt() {
		return et;
	}

	public void setEt(List<EliminationTournamentConfig> et) {
		this.et = et;
	}

	public List<EliminationTournamentConfig> getVip() {
		return vip;
	}

	public void setVip(List<EliminationTournamentConfig> vip) {
		this.vip = vip;
	}

	public List<BlockadeTournamentConfig> getBt() {
		return bt;
	}

	public void setBt(List<BlockadeTournamentConfig> bt) {
		this.bt = bt;
	}

	public Map<Long, FreeTournamentConfig> getFtMaps() {
		return ftMaps;
	}

	public void setFtMaps(Map<Long, FreeTournamentConfig> ftMaps) {
		this.ftMaps = ftMaps;
	}

	public Map<Long, EliminationTournamentConfig> getEtMaps() {
		return etMaps;
	}

	public void setEtMaps(Map<Long, EliminationTournamentConfig> etMaps) {
		this.etMaps = etMaps;
	}

	public Map<Long, EliminationTournamentConfig> getVipMaps() {
		return vipMaps;
	}

	public void setVipMaps(Map<Long, EliminationTournamentConfig> vipMaps) {
		this.vipMaps = vipMaps;
	}

	public Map<Long, BlockadeTournamentConfig> getBtMaps() {
		return btMaps;
	}

	public void setBtMaps(Map<Long, BlockadeTournamentConfig> btMaps) {
		this.btMaps = btMaps;
	}

	

}
