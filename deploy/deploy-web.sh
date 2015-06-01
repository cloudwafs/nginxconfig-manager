#!/usr/bin/expect
cd /tmp/loadbalance-manager
set WEB_HOST [lindex $argv 0]
set USER [lindex $argv 1]
set PASSWORD [lindex $argv 2]
set DATE [lindex $argv 3]
set VERSION [lindex $argv 4]
set timeout -1

   #scp
   spawn scp deploy/target/nginx-api-${VERSION}.zip $USER@${WEB_HOST}:/dianyi/
   expect "password:" {
      send "${PASSWORD}\r"
   }
   expect "*]*"
   #login
   spawn ssh $USER@$WEB_HOST
   expect "(yes/no)?" {
         send "yes\r"
         expect "password:" {
             send "$PASSWORD\r"
         }
   } "password:" {
         send "$PASSWORD\r"
   } 
   expect "*]*"
   #stop
   send "jetty.sh stop\r"
   expect "*]*"
   #backup,unzip
   send "mkdir -p /dianyi/backup_lbs\r"
   send "rm /dianyi/backup_lbs/lbs* -rf\r"
   send "mv /dianyi/webapps/lbs /dianyi/backup_lbs/lbs.${DATE}\r"
   expect "*]*"
   send "unzip -q /dianyi/nginx-api-${VERSION}.zip -d /tmp/lbs\r"
   expect "*]*"
   send "mv /tmp/lbs/ROOT /dianyi/webapps/lbs\r"
   expect "*]*"
   #start
   send "jetty.sh restart\r"
   expect "*]*"

send "exit\r"
expect eof
exit
