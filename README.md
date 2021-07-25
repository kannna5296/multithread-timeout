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


####設定値
| 接続タイムアウト  | 読取タイムアウト |
| --- | --- |
| 3秒  | 3秒  |
