package co.shoutnet.shoutcap;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelAnswerFAQ;
import co.shoutnet.shoutcap.model.ModelQuestionFAQ;

/**
 * Created by Adam MB on 10/21/2015.
 */
public class FAQ {

    public ArrayList<ModelQuestionFAQ> getData() {

        ArrayList<ModelQuestionFAQ> questionList = new ArrayList<ModelQuestionFAQ>();
        ArrayList<ModelAnswerFAQ> answerList;
        ModelQuestionFAQ question;
        ModelAnswerFAQ answer;

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("1. Bagaimana cara memesan ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Memesan ShoutCap ada 4 cara yaitu pertama melalui website : www.shoutcap.net (versi mobile bisa diakses via smartphone), kedua melalui Line, ketiga melalui Whatsapp, keempat melalui SMS. #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("2. Apakah ShoutCap melayani order/pesanan melalui BBM, Whatsapp, Line atau layanan chatting/instant messenger lainnya?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("ShoutCap hanya melayani order via LINE dan Whatsapp, TIDAK MELAYANI PESANAN melalui BBM. #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("3. Apakah ada bukti order/pesanan jika memesan ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Setiap pemesan ShoutCap akan diberikan ID ORDER sebagai tanda bukti order/pesanan #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("4. Apakah fungsi ShoutID yang dibuat di www.shoutcap.net?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("seperti halnya username yang digunakan untuk masuk ke akun personal seseorang di social media, ShoutID adalah username kamu yang berfungsi sebagai akun personal/identitas Shouters untuk melakukan transaksi baik pemesanan ShoutCap di www.shoutcap.net maupun pembelian produk di www.shoutstore.co");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("5. Apa fungsi ID ORDER yang diberikan oleh Admin Order ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("ID ORDER adalah bukti ShoutCap sudah menerima pesanan dan ID ORDER juga digunakan jika pemesan ingin mengetahui Status Order/Pesanan dan hal lainnya yang terkait dengan Order/Pesanan #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("6. Apakah produk ShoutCap ready stock?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Order ShoutCap adalah PRE-ORDER, bukan Ready Stock, jadi kami memerlukan waktu untuk memproses pesanan dari awal : persiapan bahan, aplikasi, jahit hingga finishing #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("7. Berapa jumlah minimum order ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Tidak ada minimum order untuk memesan ShoutCap #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("8. Apakah bisa memesan ShoutCap lebih dari 1 (satu) pc?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Bisa, silakan ikuti proses pemesanan di www.shoutcap.net, pada halaman CART klik CREATE NEW SHOUTCAP #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("9. Apakah ShoutCap yang ada di Gallery pada Fanpage dan twitpic favorite tersedia dengan warna lain?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("ShoutCap yang ada di GALLERY pada fanpage, blog & twitpic favorite bisa dipesan dengan warna yang beda dari yang ada di GALLERY #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("10. Apa saja model topi yang ada di ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Saat ini model topi ShoutCap ada 2 (dua) yaitu trucker cap (mesh/jaring) dan baseball cap #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("11. Dimana bisa melihat pilihan model, warna dan jenis visor ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Pilihan model, warna dan jenis visor dapat dilihat di bagian SELECT MODEL pada aplikasi order di www.shoutcap.net dan di SHOUTCAP TYPE pada ShoutMagz. #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("12. Apakah bisa pesan dengan shout/text sendiri?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("BISA, karena konsep ShoutCap adalah customized & personalized #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("13. Saya ingin memesan ShoutCap tapi tidak punya ide Text/Shout-nya, apakah pihak ShoutCap dapat membantu memberi ide?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Silakan lihat dan pilih di bagian SUGGESTIONS di www.shoutcap.net #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("14. Berapa harga ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Harga ShoutCap mulai dari 100rb (adult size) dan 90rb (junior size) belum termasuk ongkos kirim (ongkir) #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("15. Apakah bisa pesan shoutcap dalam bentuk gambar atau logo?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("maaf, tidak bisa karena konsep ShoutCap adalah SHOUT YOUR MIND! Jadi hanya text saja #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("16. Dimana bisa melihat shout/text yang kami tawarkan sebagai alternatif?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Lihat di bagian SUGGESTIONS pada aplikasi order di www.shoutcap.net #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("17. Apakah shout/text di bagian SUGGESTIONS pada aplikasi order bisa dipesan?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Bisa, silakan klik pada shout/text yang diinginkan #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("18. Apakah bisa memilih/request FONT yang lain?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("maaf, tidak bisa, hanya yang tersedia di bagian SELECT FONT di www.shoutcap.net #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("19. Berapa karakter/huruf yang bisa ditampilkan pada ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Silakan dicoba langsung di www.shoutcap.net , tetapi sebaiknya tidak lebih dari 40 karakter agar masih dapat terbaca dengan jelas #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("20. Bagaimana jika terjadi kesalahan Order, apakah order saya bisa dibatalkan atau harus mengulang order?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Ada dua hal, pertama, Order yang sudah dibayar tidak dapat dibatalkan dengan alasan apapun dan pembayaran yang sudah ditransfer tidak dapat dikembalikan. Jika terjadi kesalahan order, silakan mengisi form CONTACT US untuk menginformasikan no order yang direvisi. Kedua, jika sudah submit order tapi belum dibayar/transfer, dan terjadi kesalahan order, silakan mengulang order, kemudian mengisi form CONTACT US untuk menginformasikan no order yang dibatalkan dan no order penggantinya.");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("21. Apakah bisa pesan ShoutCap tanpa shout/text?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Bisa, namanya No-ShoutCap #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("22. Berapa harga No-ShoutCap?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Harga No-ShoutCap untuk varian Classic dan Color adalah 70rb (adult size) dan 60rb (junior size). Untuk varian Mixed adalah 75rb (adult size) dan 65rb (junior size), semuanya belum termasuk ongkos kirim (ongkir) #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("23. Jasa kurir mana yang digunakan ShoutCap untuk delivery?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("ShoutCap menggunakan jasa JNE sebagai kurir dengan tarif Reguler untuk mengirimkan pesanan #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("24. Berapa lama waktu pengiriman pesanan?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Waktu pengiriman sesuai dengan ketentuan yang berlaku di JNE untuk jenis Pengiriman Reguler #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("25. Berapa lama saya akan mendapat konfirmasi setelah melakukan pesanan?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Pesanan yang masuk akan direspon dalam waktu 1x24 jam melalui email dan sms, KECUALI order yang masuk hari SABTU & MINGGU akan direspon pada hari Senin. Semua RESPON akan dilakukan pada jam kerja (09.00-16.00) #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("26. Apakah order saya langsung diproses setelah melakukan pemesanan?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Order diproses setelah pemesan MELAKUKAN KONFIRMASI PEMBAYARAN dan setelah pembayaran diterima oleh ShoutCap . Setiap order akan diproses maksimal 10 hari kerja #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("27. Bagaimana order saya diproses?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Setiap minggu semua order akan diproses dan diterima pemesan maksimal 10 hari kerja (tergantung pada waktu pengiriman JNE). 10 hari kerja dihitung dari konfirmasi pembayaran oleh pemesan karena order baru akan diproses setelah pemesan mengkonfirmasi pembayaran #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("28. Bagaimana cara melakukan KONFIRMASI PEMBAYARAN?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Setelah pemesan melakukan pembayaran, silakan mengkonfirmasi pembayaran dengan cara mengisi & submit FORM ini #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("29. Berapa lama waktu yang diberikan untuk melakukan pembayaran?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Invoice yang kamu terima akan berlaku selama 6 hari kerja. Jika dalam waktu 6 hari tidak ada pembayaran, pesanan dianggap batal. Silakan mengulang pesanan jika tetap akan memesan #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("30. Bagaimana konfirmasi pemesanan diproses?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("SMS konfirmasi pembayaran yang masuk jam 09.00-16.00 akan direspon maksimal jam 17.00 pada hari yang sama. Order yang masuk diluar jam tersebut akan direspon maksimal jam 17.00 esok harinya #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("31. Bagaimana jika konfirmasi dilakukan diluar hari/jam kerja?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Konfirmasi pembayaran/transfer yang dilakukan hari Sabtu & Minggu atau hari libur lainnya, akan dikonfirmasi hari Senin atau hari kerja berikutnya antara jam 09.00-16.00 #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("32. Apakah ShoutCap memiliki layanan konsumen?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Untuk pertanyaan seputar status pesanan & pengiriman, silakan SMS ke Customer Service di 081931434800 pd jam 09.00-17.00 (Senin-Jumat), 09.00-12.00 (Sabtu). ShoutCap tidak mempunyai no telepon seluler lain selain no tersebut #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);

        answerList = new ArrayList<ModelAnswerFAQ>();
        question = new ModelQuestionFAQ();
        question.setQuestion("33. Apakah ShoutCap membuka sistem Reseller atau Keagenan/Cabang untuk distribusi?");
        answer = new ModelAnswerFAQ();
        answer.setAnswer("Sampai saat ini ShoutCap TIDAK MEMPUNYAI RESELLER atau AGEN atau CABANG #SHOUTCAPFAQ");
        answerList.add(answer);
        question.setItems(answerList);
        questionList.add(question);


        return questionList;
    }
}
