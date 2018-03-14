/**
 * 
 */
package com.sky.game.service.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.game.service.domain.Sequence;
import com.sky.game.service.persistence.SequenceMapper;

/**
 * @author Administrator
 *
 */
@Service
public class SequenceService {
	
	private static final Log logger = LogFactory.getLog(ExchangeService.class);
	
	@Autowired
	SequenceMapper sequenceMapper;

	/**
	 * 
	 */
	public SequenceService() {
		// TODO Auto-generated constructor stub
	}
	/*private static final String USER_ACCOUNT_ID="user_account_id";
	
	private static final String USER_ITEM_LOG_ID="user_item_log_id";
	private static final String USER_PROPERTY_LOG_ID="user_property_log_id";
	private static final String BLOCKADE_USER_ID="blockade_user_id";
	private static final String BLOCKADE_MESSAGE_MAP_ID="blockade_message_map_id";
	private static final String BLOCKADE_MESSAGE_ID="blockade_message_id";*/
	
	private static final String YOU_KE_ID="you_ke_id";

	
	private Long gen(String id){
		sequenceMapper.updateAutoincrement(id);
		Sequence seq=sequenceMapper.selectByPrimaryKey(id);
		return seq.getSeqCount();
	}
	/**
	 * 
	 * generate the user account id.
	 * 
	 * @return
	 */
	public Long generateYouKeId(){
		return gen(YOU_KE_ID);
	}
//	public Long generateUserAccountId(){
//		
//		return gen(USER_ACCOUNT_ID);
//	} 
//	
//
//	/**
//	 * 
//	 * generate the user item log id.
//	 * 
//	 * @return
//	 */
//	
//	public Long generateUserItemLogId(){
//		return gen(USER_ITEM_LOG_ID);
//	} 
//	
//	
//	/**
//	 * 
//	 * generate the user item log id.
//	 * 
//	 * @return
//	 */
//	
//	public Long generateUserPropertyLogId(){
//		
//		 return gen(USER_PROPERTY_LOG_ID);
//	} 
//
//	
//	public Long genGameBlockadeUserId(){
//		return gen(BLOCKADE_USER_ID);
//	}
//	
//	public Long genGameMessageId(){
//		return gen(BLOCKADE_MESSAGE_ID);
//	}
//	public Long genGameMessageMapId(){
//		return gen(BLOCKADE_MESSAGE_MAP_ID);
//	}

}
