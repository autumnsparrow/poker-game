#!/bin/sh

function remote_cmd {
	echo ">> $1"
	ssh -p 31005 dbgame@game.sky-game.com $1	
}



function list {
	echo "Impot - import the online <sky_platform> database into the local <192.168.1.150>";
	echo "Export - export the sky_right table to online <sky_gm> database [game.sky-game.com]."
}

PWD=`pwd`
echo $PWD
function export_right {
	rm -fr $PWD/sky_right.sql
	mysqldump -uroot sky_gm sky_right >sky_right.sql
	TMP=`date +%m%d%H%M.sql`
	echo $TMP
	echo "use sky_gm;">>/tmp/$TMP
	cat sky_right.sql>>/tmp/$TMP
	mv /tmp/$TMP sky_right.sql
	scp -P 31005 $PWD/sky_right.sql dbgame@game.sky-game.com:/home/dbgame/mysql.bk
	ssh -p 31005 dbgame@game.sky-game.com  '/home/dbgame/mysql/bin/mysql -uroot -h192.168.10.25  --password=5tgbhu8ik,lp- </home/dbgame/mysql.bk/sky_right.sql'

	echo "delete from sky_role_right where role_id=9;";
	echo "insert into sky_role_right(role_id,right_id) select 9,right_id from sky_right;";
	
	ssh -p 31005 dbgame@game.sky-game.com  '/home/dbgame/mysql/bin/mysql -uroot -h192.168.10.25  --password=5tgbhu8ik,lp- </home/dbgame/mysql.bk/update_right.sql'


}

SUFFIX=`date +%m%d%S.sql`
function import {
	echo "Import .. " ;
	MYSQLDUMP='/home/dbgame/mysql/bin/mysqldump'
	REMOTE_DIR='/home/dbgame/mysql.bk'
	LOCAL_DIR='/home/developer/mysql.backup'
	DB="$REMOTE_DIR/sky_platform.$SUFFIX"
	LDB="$LOCAL_DIR/sky_platform.sql"
	echo "------------------------------------------------";
	echo "$MYSQLDUMP";
	echo "$SUFFIX"
	echo "$REMOTE_DIR";
	echo "$LOCAL_DIR";
	echo "$DB";	
	echo "------------------------------------------------";
		
 			
	remote_cmd 'rm -f /home/dbgame/mysql.bk/sky_platform.sql'
	remote_cmd '/home/dbgame/mysql/bin/mysqldump -uroot -h192.168.10.25 --password=5tgbhu8ik,lp- sky_platform>/home/dbgame/mysql.bk/sky_platform.sql'
	
	scp -P 31005 dbgame@game.sky-game.com:/home/dbgame/mysql.bk/sky_platform.sql $LOCAL_DIR/sky_platform.sql
	
	mysql -uroot -e"drop database sky_platform"
	mysql -uroot -e"create database sky_platform"
	echo "use sky_platform;">/tmp/$SUFFIX.sql
	cat $LDB >>/tmp/$SUFFIX.sql
	mv /tmp/$SUFFIX.sql $LDB
	mysql -uroot < $LDB	
	mv $LDB $LDB.$SUFFIX.bk

}
import;
