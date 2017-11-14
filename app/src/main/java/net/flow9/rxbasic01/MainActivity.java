package net.flow9.rxbasic01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    CustomAdapter adapter;

    // 데이터 저장 변수
    List<String> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 데이터 - 인터넷에서 순차적으로 가져오는것
        String data[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        // 1. 발행자 생성 operator from
        Observable<String> observableFrom = Observable.fromArray(data);

        // 2. 구독자
//        observableFrom.subscribe(new Consumer<String>() {    // onNext 데이터가 있으면 호출된다
//            @Override
//            public void accept(String s) throws Exception {
//                months.add(s);
//            }
//        }, new Consumer<Throwable>() {    // onError 가 호출된다.
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        }, new Action() { // onComplete 이 호출된다.
//            @Override
//            public void run() throws Exception {
//                adapter.setDataAndRefresh(months);
//            }
//        });
        observableFrom.subscribe(
                str -> months.add(str),
                throwble -> {},
                () -> adapter.setDataAndRefresh(months) );
    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    List<String> data = new ArrayList<>();
    public void setDataAndRefresh(List<String> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,parent, false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.text1.setText(data.get(position));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        TextView text1;
        public Holder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }
    }
}







