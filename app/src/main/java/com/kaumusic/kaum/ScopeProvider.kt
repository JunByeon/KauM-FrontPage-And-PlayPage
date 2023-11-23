package com.kaumusic.kaum

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// 스코프를 통일해서  비동기작업을 일괄된 스코프로 관리하기 위해서
object ScopeProvider {
    // 코루틴
    // DB는 IO 쓰레드에서 일괄적으로 관리하기 위해 scope를 생성 (databaseScope 비동기적인 작업 할 수 있음 async(비동적인 작업의 결과가 필요할 때), launch(실행)
     val databaseScope = CoroutineScope(Dispatchers.IO)
}