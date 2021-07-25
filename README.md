# multithread-timeout
JavaAPIのタイムアウト + マルチスレッド処理のテスト実装<br>
## Server側
・呼び出されるAPI側. Userエンティティ(固定値)を返す。 <br>
・処理内で5秒Thread.sleepさせ、Clientから呼び出された際にタイムアウトを起こさせる
<br><br>

## Client側
・APIを呼ぶ側 <br>
・SpringBootのRestTemplateを用いてAPI通信を行う<br>
・タイムアウトが起きた場合、ThreadPoolTaskExecutorを用いて新規スレッドを立ち上げ、非同期タスク処理を開始する。スレッド立ち上げはforループにより10回行う。<br>
・スレッドが立ち上げられなかった場合(RejectedExecutionException)、タスクが実行できなかった旨を標準出力に出力する
<br><br>
##### 非同期タスク処理（com.example.timeout.server.task.MyTask.java)
引数：loopの回数、待ち時間<br>
Runnableインターフェースを実装し、runメソッドをオーバーライドして下記の処理を行う。<br>
<br>
・ タスク開始宣言を標準出力に出力する<br>
・ Thread.sleepメソッドにより、引数で得た待ち時間だけスレッドを待機させる<br>
・ タスク終了宣言を標準出力に出力する<br>
<br>

#### 設定値
| 接続タイムアウト  | 読取タイムアウト |
| --- | --- |
| 3秒  | 3秒  |
<br><br>


## 標準出力例
【接続URL】localhost:8080/?syncName=wwwww<br>
**TaskExecutorの同時接続数が3なので、連続で3つ以上のスレッドが立ち上がらず失敗している。**
<br><br>
Submit [0]<br>
Submit [1]<br>
Submit [2]<br>
+++ Begin [0] on wwwww-1 at 17:18:35 +++<br>
+++ Begin [2] on wwwww-3 at 17:18:35 +++<br>
+++ Begin [1] on wwwww-2 at 17:18:35 +++<br>
Submit [3]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@3e057dd6<br>
Submit [4]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@13f5268b<br>
Submit [5]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@574d0cfe<br>
Submit [6]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@112dab98<br>
Submit [7]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@75457afc<br>
Submit [8]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@120d0459<br>
Submit [9]<br>
タスク実行できませんでしたExecutor [java.util.concurrent.ThreadPoolExecutor@56d2fed8[Running, pool size = 3, active threads = 3, queued tasks = 0, completed tasks = 0]] did not accept task: com.example.timeout.server.task.MyTask@3f3ed68<br>
+++ End [2] on wwwww-3 at 17:18:35 +++<br>
+++ End [0] on wwwww-1 at 17:18:36 +++<br>
+++ End [1] on wwwww-2 at 17:18:37 +++<br>