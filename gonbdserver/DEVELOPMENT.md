```
;; create a backing file
$ dd if=/dev/zero of=nbd.file bs=4096 count=1000000

;; build the server
$ go build && ./gonbdserver -c gonbdserver.conf -f
gonbdserver:[INFO] Loaded configuration. Available backends: aiofile, file, rbd.
gonbdserver:[INFO] Starting server tcp:127.0.0.1:6666
gonbdserver:[INFO] Starting listening on tcp:127.0.0.1:6666
gonbdserver:[INFO] Connect to tcp:127.0.0.1:6666 from 127.0.0.1:37836
find export [102 111 111]
gonbdserver:[INFO] Negotiation succeeded with 127.0.0.1:37836/foo, serving with 5 worker(s)


$ sudo modprobe nbd
$ sudo nbd-client -N foo localhost 6666 /dev/nbd0 -b 4096
[sudo] password for mping: 
Negotiation: ..size = 0MB
Connected /dev/nbd0

;; this isrun as superuser
;; only need to mkfs once
root@3700x:~# mkfs.ext4 -b 4096 /dev/nbd0

;;
root@3700x:~# mkdir -p /mnt/nbd-mount
root@3700x:~# mount /dev/nbd0 /mnt/nbd-mount/


;; to disconnect:
$ sudo umount /mnt/nbd-mount
$ sudo nbd-client -d /dev/nbd0



```