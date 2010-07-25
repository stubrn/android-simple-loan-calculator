package ee.smkv.calc.loan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;


/**
 * @author Andrei Samkov
 */
public class Schedule extends Activity {
  int mode = BigDecimal.ROUND_HALF_UP;
  TableLayout table;
  TableRow header , footer;
  Button closeButton , chartButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.schedule);
    table = (TableLayout)findViewById(R.id.ScheduleTable);
    header = (TableRow)findViewById(R.id.HeaderRow);
    footer = (TableRow)findViewById(R.id.FooterRow);
    closeButton = (Button)findViewById(R.id.scheduleClose);
    closeButton.setOnClickListener( new  View.OnClickListener(){

      public void onClick(View view) {
        finish();
      }
    });
    chartButton = (Button)findViewById(R.id.chartButton);
    chartButton.setOnClickListener( new  View.OnClickListener(){

        public void onClick(View view) {
        	Loan loan = getLoan();
        	Intent chart = new Intent(Schedule.this, ChartActivity.class);
        	chart.putExtra(Loan.class.getName(), loan);
            startActivity(chart);
        }
      });
    show();
  }

  @Override
  protected void onResume() {
    super.onResume();
    
  }

  private void show() {
    Loan loan = getLoan();
    clearTable();
    showSchedule(loan);
  }

private Loan getLoan() {
	Loan loan = (Loan)getIntent().getSerializableExtra(Loan.class.getName());
	return loan;
}

  private void clearTable() {
    for (int i = 0 ; i < table.getChildCount() ; i ++ ){
      View view = table.getChildAt(i);
      if (view instanceof TableRow && view != header && view != footer ){
        table.removeView(view);
      }
    }
  }

  private void showSchedule(Loan loan) {
    int pos = 1;
    for (Payment payment : loan.getPayments()){
      addPaymentToTable(payment , pos ++);
    }
  }

  private void addPaymentToTable(Payment payment, int pos) {
    TableRow row = new TableRow(table.getContext());
    table.addView(row , pos);
    TextView nr = new TextView(row.getContext());
    TextView balance = new TextView(row.getContext());
    TextView principal = new TextView(row.getContext());
    TextView interest = new TextView(row.getContext());
    TextView amount = new TextView(row.getContext());

    nr.setText(payment.getNr().toString());
    balance.setText(payment.getBalance().setScale(2,mode).toPlainString());
    principal.setText(payment.getPrincipal().setScale(2,mode).toPlainString());
    interest.setText(payment.getInterest().setScale(2,mode).toPlainString());
    amount.setText(payment.getAmount().setScale(2,mode).toPlainString());

    row.addView(nr);
    row.addView(balance);
    row.addView(principal);
    row.addView(interest);
    row.addView(amount);
  }
}