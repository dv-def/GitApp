package ru.dvn.gitapp.ui.users.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class RxButton(context: Context, attr: AttributeSet)
    : MaterialButton(context, attr) {
    val btnObservable: Observable<Unit> = BehaviorSubject.create()

    init {
        setOnClickListener {
            btnObservable.asSubject().onNext(Unit)
        }
    }

    private fun <T: Any> Observable<T>.asSubject(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("RxButton. This is not an Observable")
    }
}