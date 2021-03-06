// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager.web.security;

import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import com.sky.game.manager.filter.UserService;
import com.sky.game.manager.model.security.Authority;
import com.sky.game.manager.model.security.AuthorityPrincipalAssignment;
import com.sky.game.manager.model.security.Channel;
import com.sky.game.manager.model.security.ChannelPrincipalAssignment;
import com.sky.game.manager.model.security.Principal;
import com.sky.game.manager.remote.activerecord.UserAccount;
import com.sky.game.manager.remote.activerecord.UserBank;
import com.sky.game.manager.web.security.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    UserService ApplicationConversionServiceFactoryBean.userService;
    
    public Converter<UserActiveDayliyStatics, String> ApplicationConversionServiceFactoryBean.getUserActiveDayliyStaticsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.activerecord.UserActiveDayliyStatics, java.lang.String>() {
            public String convert(UserActiveDayliyStatics userActiveDayliyStatics) {
                return new StringBuilder().append(userActiveDayliyStatics.getDay2Active()).append(' ').append(userActiveDayliyStatics.getDay3Active()).append(' ').append(userActiveDayliyStatics.getDay7Active()).append(' ').append(userActiveDayliyStatics.getDay30Active()).toString();
            }
        };
    }
    
    public Converter<Long, UserActiveDayliyStatics> ApplicationConversionServiceFactoryBean.getIdToUserActiveDayliyStaticsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.activerecord.UserActiveDayliyStatics>() {
            public com.sky.game.manager.activerecord.UserActiveDayliyStatics convert(java.lang.Long id) {
                return UserActiveDayliyStatics.findUserActiveDayliyStatics(id);
            }
        };
    }
    
    public Converter<String, UserActiveDayliyStatics> ApplicationConversionServiceFactoryBean.getStringToUserActiveDayliyStaticsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.activerecord.UserActiveDayliyStatics>() {
            public com.sky.game.manager.activerecord.UserActiveDayliyStatics convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserActiveDayliyStatics.class);
            }
        };
    }
    
    public Converter<Authority, String> ApplicationConversionServiceFactoryBean.getAuthorityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.model.security.Authority, java.lang.String>() {
            public String convert(Authority authority) {
                return new StringBuilder().append(authority.getRoleId()).append(' ').append(authority.getRole()).toString();
            }
        };
    }
    
    public Converter<Long, Authority> ApplicationConversionServiceFactoryBean.getIdToAuthorityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.model.security.Authority>() {
            public com.sky.game.manager.model.security.Authority convert(java.lang.Long id) {
                return Authority.findAuthority(id);
            }
        };
    }
    
    public Converter<String, Authority> ApplicationConversionServiceFactoryBean.getStringToAuthorityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.model.security.Authority>() {
            public com.sky.game.manager.model.security.Authority convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authority.class);
            }
        };
    }
    
    public Converter<AuthorityPrincipalAssignment, String> ApplicationConversionServiceFactoryBean.getAuthorityPrincipalAssignmentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.model.security.AuthorityPrincipalAssignment, java.lang.String>() {
            public String convert(AuthorityPrincipalAssignment authorityPrincipalAssignment) {
                return "(no displayable fields)";
            }
        };
    }
    
    public Converter<Long, AuthorityPrincipalAssignment> ApplicationConversionServiceFactoryBean.getIdToAuthorityPrincipalAssignmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.model.security.AuthorityPrincipalAssignment>() {
            public com.sky.game.manager.model.security.AuthorityPrincipalAssignment convert(java.lang.Long id) {
                return AuthorityPrincipalAssignment.findAuthorityPrincipalAssignment(id);
            }
        };
    }
    
    public Converter<String, AuthorityPrincipalAssignment> ApplicationConversionServiceFactoryBean.getStringToAuthorityPrincipalAssignmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.model.security.AuthorityPrincipalAssignment>() {
            public com.sky.game.manager.model.security.AuthorityPrincipalAssignment convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), AuthorityPrincipalAssignment.class);
            }
        };
    }
    
    public Converter<Channel, String> ApplicationConversionServiceFactoryBean.getChannelToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.model.security.Channel, java.lang.String>() {
            public String convert(Channel channel) {
                return new StringBuilder().append(channel.getChannelId()).append(' ').append(channel.getCreateTime()).toString();
            }
        };
    }
    
    public Converter<Long, Channel> ApplicationConversionServiceFactoryBean.getIdToChannelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.model.security.Channel>() {
            public com.sky.game.manager.model.security.Channel convert(java.lang.Long id) {
                return Channel.findChannel(id);
            }
        };
    }
    
    public Converter<String, Channel> ApplicationConversionServiceFactoryBean.getStringToChannelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.model.security.Channel>() {
            public com.sky.game.manager.model.security.Channel convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Channel.class);
            }
        };
    }
    
    public Converter<ChannelPrincipalAssignment, String> ApplicationConversionServiceFactoryBean.getChannelPrincipalAssignmentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.model.security.ChannelPrincipalAssignment, java.lang.String>() {
            public String convert(ChannelPrincipalAssignment channelPrincipalAssignment) {
                return "(no displayable fields)";
            }
        };
    }
    
    public Converter<Long, ChannelPrincipalAssignment> ApplicationConversionServiceFactoryBean.getIdToChannelPrincipalAssignmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.model.security.ChannelPrincipalAssignment>() {
            public com.sky.game.manager.model.security.ChannelPrincipalAssignment convert(java.lang.Long id) {
                return userService.findChannelPrincipalAssignment(id);
            }
        };
    }
    
    public Converter<String, ChannelPrincipalAssignment> ApplicationConversionServiceFactoryBean.getStringToChannelPrincipalAssignmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.model.security.ChannelPrincipalAssignment>() {
            public com.sky.game.manager.model.security.ChannelPrincipalAssignment convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ChannelPrincipalAssignment.class);
            }
        };
    }
    
    public Converter<Principal, String> ApplicationConversionServiceFactoryBean.getPrincipalToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.model.security.Principal, java.lang.String>() {
            public String convert(Principal principal) {
                return new StringBuilder().append(principal.getUsername()).append(' ').append(principal.getPassword()).toString();
            }
        };
    }
    
    public Converter<Long, Principal> ApplicationConversionServiceFactoryBean.getIdToPrincipalConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.model.security.Principal>() {
            public com.sky.game.manager.model.security.Principal convert(java.lang.Long id) {
                return Principal.findPrincipal(id);
            }
        };
    }
    
    public Converter<String, Principal> ApplicationConversionServiceFactoryBean.getStringToPrincipalConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.model.security.Principal>() {
            public com.sky.game.manager.model.security.Principal convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Principal.class);
            }
        };
    }
    
    public Converter<UserAccount, String> ApplicationConversionServiceFactoryBean.getUserAccountToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.remote.activerecord.UserAccount, java.lang.String>() {
            public String convert(UserAccount userAccount) {
                return new StringBuilder().append(userAccount.getName()).append(' ').append(userAccount.getNickName()).append(' ').append(userAccount.getUserPassword()).append(' ').append(userAccount.getUpdateTime()).toString();
            }
        };
    }
    
    public Converter<Long, UserAccount> ApplicationConversionServiceFactoryBean.getIdToUserAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.remote.activerecord.UserAccount>() {
            public com.sky.game.manager.remote.activerecord.UserAccount convert(java.lang.Long id) {
                return UserAccount.findUserAccount(id);
            }
        };
    }
    
    public Converter<String, UserAccount> ApplicationConversionServiceFactoryBean.getStringToUserAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.remote.activerecord.UserAccount>() {
            public com.sky.game.manager.remote.activerecord.UserAccount convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserAccount.class);
            }
        };
    }
    
    public Converter<UserBank, String> ApplicationConversionServiceFactoryBean.getUserBankToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sky.game.manager.remote.activerecord.UserBank, java.lang.String>() {
            public String convert(UserBank userBank) {
                return new StringBuilder().append(userBank.getLoanDate()).append(' ').append(userBank.getRefundDate()).append(' ').append(userBank.getDeposit()).append(' ').append(userBank.getLoan()).toString();
            }
        };
    }
    
    public Converter<Long, UserBank> ApplicationConversionServiceFactoryBean.getIdToUserBankConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.sky.game.manager.remote.activerecord.UserBank>() {
            public com.sky.game.manager.remote.activerecord.UserBank convert(java.lang.Long id) {
                return UserBank.findUserBank(id);
            }
        };
    }
    
    public Converter<String, UserBank> ApplicationConversionServiceFactoryBean.getStringToUserBankConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sky.game.manager.remote.activerecord.UserBank>() {
            public com.sky.game.manager.remote.activerecord.UserBank convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserBank.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getUserActiveDayliyStaticsToStringConverter());
        registry.addConverter(getIdToUserActiveDayliyStaticsConverter());
        registry.addConverter(getStringToUserActiveDayliyStaticsConverter());
        registry.addConverter(getAuthorityToStringConverter());
        registry.addConverter(getIdToAuthorityConverter());
        registry.addConverter(getStringToAuthorityConverter());
        registry.addConverter(getAuthorityPrincipalAssignmentToStringConverter());
        registry.addConverter(getIdToAuthorityPrincipalAssignmentConverter());
        registry.addConverter(getStringToAuthorityPrincipalAssignmentConverter());
        registry.addConverter(getChannelToStringConverter());
        registry.addConverter(getIdToChannelConverter());
        registry.addConverter(getStringToChannelConverter());
        registry.addConverter(getChannelPrincipalAssignmentToStringConverter());
        registry.addConverter(getIdToChannelPrincipalAssignmentConverter());
        registry.addConverter(getStringToChannelPrincipalAssignmentConverter());
        registry.addConverter(getPrincipalToStringConverter());
        registry.addConverter(getIdToPrincipalConverter());
        registry.addConverter(getStringToPrincipalConverter());
        registry.addConverter(getUserAccountToStringConverter());
        registry.addConverter(getIdToUserAccountConverter());
        registry.addConverter(getStringToUserAccountConverter());
        registry.addConverter(getUserBankToStringConverter());
        registry.addConverter(getIdToUserBankConverter());
        registry.addConverter(getStringToUserBankConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
