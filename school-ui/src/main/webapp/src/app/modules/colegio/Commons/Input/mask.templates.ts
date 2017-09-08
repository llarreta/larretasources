//Mask
import createNumberMask from 'text-mask-addons/dist/createNumberMask'
import emailMask from 'text-mask-addons/dist/emailMask'

export class MaskTemplates{    

    public static DNI = [ /[ 0-9 ]/ , /[ 0-9 ]/ , '.' , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ , '.' , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ ];
    public static TELPHONE = [ /[ 0-9 ]/ , /[ 0-9 ]/ , '-' , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ , '-' , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ , /[ 0-9 ]/ ];
    public static MAIL = emailMask;
    public static price = createNumberMask({
                                                prefix: '$',
                                                suffix: '',
                                                includeThousandsSeparator: true,
                                                thousandsSeparatorSymbol: ".",
                                                decimalSymbol: ",",
                                                decimalLimit: 2,
                                                allowNegative: true,
                                                allowLeadingZeroes: false,
                                                allowDecimal: true
                                            })

}