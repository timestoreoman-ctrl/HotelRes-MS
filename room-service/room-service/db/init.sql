volumes:
  - roomdb_data:/var/lib/mysql
  - ./db/init.sql:/docker-entrypoint-initdb.d/init.sql
