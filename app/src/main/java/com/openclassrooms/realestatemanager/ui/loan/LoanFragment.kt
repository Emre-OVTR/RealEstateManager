package com.openclassrooms.realestatemanager.ui.loan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding


class LoanFragment : Fragment() {


    private lateinit var fragmentLoanBinding: FragmentLoanBinding

    //instancier textviews et button
    // clcicklistener sur button qui declenche un calcul
    // affichage des resultats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentLoanBinding = FragmentLoanBinding.inflate(layoutInflater)
        fragmentLoanBinding.loanCalculate.setOnClickListener {
            calculate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return fragmentLoanBinding.root
    }




    private fun calculate(){
        var canCalculate = false
        val amount = fragmentLoanBinding.loanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val downPayment = fragmentLoanBinding.loanDown.text.toString().toDoubleOrNull() ?: 0.0
        val term = fragmentLoanBinding.loanTerm.text.toString().toDoubleOrNull()
        val interest = fragmentLoanBinding.loanInterest.text.toString().toDoubleOrNull()

        when{
            fragmentLoanBinding.loanAmount.text.isNullOrEmpty() || fragmentLoanBinding.loanTerm.text.isNullOrEmpty() || fragmentLoanBinding.loanInterest.text.isNullOrEmpty() || downPayment >= amount!! -> {
                canCalculate = false
                if (fragmentLoanBinding.loanAmount.text.isNullOrEmpty()){
                   fragmentLoanBinding.loanAmountLayout.error = resources.getString(R.string.loan_error)
                }
                if (fragmentLoanBinding.loanTerm.text.isNullOrEmpty()){
                    fragmentLoanBinding.loanTermLayout.error = resources.getString(R.string.loan_error)
                }
                if (fragmentLoanBinding.loanInterest.text.isNullOrEmpty()){
                    fragmentLoanBinding.loanInterestLayout.error = resources.getString(R.string.loan_error)
                }else if (interest!! < 0 || interest > 100){
                    fragmentLoanBinding.loanInterestLayout.error = resources.getString(R.string.loan_error_interest_value)
                }
                if (downPayment != null && downPayment >= amount!!){
                    fragmentLoanBinding.loanDownLayout.error = resources.getString(R.string.loan_error_down_payment)
                }
            }
            else -> {
                canCalculate = true
                fragmentLoanBinding.loanAmountLayout.error = null
                fragmentLoanBinding.loanTermLayout.error = null
                fragmentLoanBinding.loanInterest.error = null
                fragmentLoanBinding.loanDownLayout.error = null
            }
        }

        if (canCalculate){
            val result: Double
            val totalPrice: Double
            if(interest == 0.0){
                result = (if (downPayment != null) ( amount!!-(downPayment)) else amount)!! / (term!! * 12)
                totalPrice = 0.0
            }else{
                result = (if (downPayment != null) (amount!! -(downPayment)) else amount)!! * ((interest!! / (100)) / (12)) / (1 - Math.pow( 1 + ((interest / 100) / 12), -term!! *12))
                totalPrice = 12 * term * result - (if (downPayment != null ) amount?.minus(downPayment)!! else amount!!)
            }
            fragmentLoanBinding.loanMonthly.setText(String.format("%.2f",result), TextView.BufferType.EDITABLE)
            fragmentLoanBinding.loanTotal.setText(String.format("%.2f",totalPrice), TextView.BufferType.EDITABLE)

        }
    }
}