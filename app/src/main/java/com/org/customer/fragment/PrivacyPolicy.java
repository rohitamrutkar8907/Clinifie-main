package com.org.customer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.clinify.R;

public class PrivacyPolicy extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        String str = "PRIVACY NOTICE\n" +
                "\n" +
                "Last updated December 29, 2020\n" +
                "\n" +
                "\n" +
                "\n" +
                "Thank you for choosing to be part of our community at Clinify (\"Company\", \"we\", \"us\", \"our\"). We are committed to protecting your personal information and your right to privacy. If you have any questions or concerns about this privacy notice, or our practices with regards to your personal information, please contact us at __________.\n" +
                "\n" +
                "When you use our mobile application, as the case may be (the \"App\") and more generally, use any of our services (the \"Services\", which include the App), we appreciate that you are trusting us with your personal information. We take your privacy very seriously. In this privacy notice, we seek to explain to you in the clearest way possible what information we collect, how we use it and what rights you have in relation to it. We hope you take some time to read through it carefully, as it is important. If there are any terms in this privacy notice that you do not agree with, please discontinue use of our Services immediately.\n" +
                "\n" +
                "This privacy notice applies to all information collected through our Services (which, as described above, includes our App), as well as, any related services, sales, marketing or events.\n" +
                "\n" +
                "Please read this privacy notice carefully as it will help you understand what we do with the information that we collect.\n" +
                "\n" +
                "TABLE OF CONTENTS\n" +
                "\n" +
                "1. WHAT INFORMATION DO WE COLLECT?\n" +
                "2. HOW DO WE USE YOUR INFORMATION?\n" +
                "3. WILL YOUR INFORMATION BE SHARED WITH ANYONE?\n" +
                "4. DO WE USE COOKIES AND OTHER TRACKING TECHNOLOGIES?\n" +
                "5. DO WE USE GOOGLE MAPS PLATFORM APIS?\n" +
                "6. HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                "7. WHAT IS OUR STANCE ON THIRD-PARTY WEBSITES?\n" +
                "8. HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                "9. HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                "10. DO WE COLLECT INFORMATION FROM MINORS?\n" +
                "11. WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                "12. CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                "13. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                "14. DO WE MAKE UPDATES TO THIS NOTICE?\n" +
                "15. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\n" +
                "16. HOW CAN YOU REVIEW, UPDATE OR DELETE THE DATA WE COLLECT FROM YOU?\n" +
                "\n" +
                "1. WHAT INFORMATION DO WE COLLECT?\n" +
                "\n" +
                "Personal information you disclose to us\n" +
                "\n" +
                "In Short:  We collect personal information that you provide to us.\n" +
                "\n" +
                "We collect personal information that you voluntarily provide to us when you register on the App, express an interest in obtaining information about us or our products and Services, when you participate in activities on the App (such as by posting messages in our online forums or entering competitions, contests or giveaways) or otherwise when you contact us.\n" +
                "\n" +
                "The personal information that we collect depends on the context of your interactions with us and the App, the choices you make and the products and features you use. The personal information we collect may include the following:\n" +
                "\n" +
                "Personal Information Provided by You. We collect names; phone numbers; email addresses; mailing addresses; job titles; usernames; passwords; contact preferences; contact or authentication data; billing addresses; debit/credit card numbers; and other similar information.\n" +
                "\n" +
                "Payment Data. We may collect data necessary to process your payment if you make purchases, such as your payment instrument number (such as a credit card number), and the security code associated with your payment instrument. All payment data is stored by Razorpay. You may find their privacy notice link(s) here: https://razorpay.com/privacy/?version=t2.\n" +
                "\n" +
                "Social Media Login Data. We may provide you with the option to register with us using your existing social media account details, like your Facebook, Twitter or other social media account. If you choose to register in this way, we will collect the Information described in the section called \"HOW DO WE HANDLE YOUR SOCIAL LOGINS\" below.\n" +
                "\n" +
                "All personal information that you provide to us must be true, complete and accurate, and you must notify us of any changes to such personal information.\n" +
                "\n" +
                "Information automatically collected\n" +
                "\n" +
                "In Short:  Some information — such as your Internet Protocol (IP) address and/or browser and device characteristics — is collected automatically when you visit our App.\n" +
                "\n" +
                "We automatically collect certain information when you visit, use or navigate the App. This information does not reveal your specific identity (like your name or contact information) but may include device and usage information, such as your IP address, browser and device characteristics, operating system, language preferences, referring URLs, device name, country, location, information about how and when you use our App and other technical information. This information is primarily needed to maintain the security and operation of our App, and for our internal analytics and reporting purposes.\n" +
                "\n" +
                "Like many businesses, we also collect information through cookies and similar technologies.\n" +
                "\n" +
                "The information we collect includes:\n" +
                "Log and Usage Data. Log and usage data is service-related, diagnostic, usage and performance information our servers automatically collect when you access or use our App and which we record in log files. Depending on how you interact with us, this log data may include your IP address, device information, browser type and settings and information about your activity in the App (such as the date/time stamps associated with your usage, pages and files viewed, searches and other actions you take such as which features you use), device event information (such as system activity, error reports (sometimes called 'crash dumps') and hardware settings).\n" +
                "Device Data. We collect device data such as information about your computer, phone, tablet or other device you use to access the App. Depending on the device used, this device data may include information such as your IP address (or proxy server), device and application identification numbers, location, browser type, hardware model Internet service provider and/or mobile carrier, operating system and system configuration information.\n" +
                "Location Data. We collect location data such as information about your device's location, which can be either precise or imprecise. How much information we collect depends on the type and settings of the device you use to access the App. For org, we may use GPS and other technologies to collect geolocation data that tells us your current location (based on your IP address). You can opt out of allowing us to collect this information either by refusing access to the information or by disabling your Location setting on your device. Note however, if you choose to opt out, you may not be able to use certain aspects of the Services.\n" +
                "\n" +
                "Information collected through our App\n" +
                "\n" +
                "In Short:  We collect information regarding your geo-location, mobile device, push notifications, when you use our App.\n" +
                "\n" +
                "If you use our App, we also collect the following information:\n" +
                "Geo-Location Information. We may request access or permission to and track location-based information from your mobile device, either continuously or while you are using our App, to provide certain location-based services. If you wish to change our access or permissions, you may do so in your device's settings.\n" +
                "Mobile Device Access. We may request access or permission to certain features from your mobile device, including your mobile device's storage, sms messages, contacts, microphone, camera, social media accounts, and other features. If you wish to change our access or permissions, you may do so in your device's settings.\n" +
                "Mobile Device Data. We automatically collect device information (such as your mobile device ID, model and manufacturer), operating system, version information and system configuration information, device and application identification numbers, browser type and version, hardware model Internet service provider and/or mobile carrier, and Internet Protocol (IP) address (or proxy server). If you are using our App, we may also collect information about the phone network associated with your mobile device, your mobile device’s operating system or platform, the type of mobile device you use, your mobile device’s unique device ID and information about the features of our App you accessed.\n" +
                "Push Notifications. We may request to send you push notifications regarding your account or certain features of the App. If you wish to opt-out from receiving these types of communications, you may turn them off in your device's settings.\n" +
                "This information is primarily needed to maintain the security and operation of our App, for troubleshooting and for our internal analytics and reporting purposes.\n" +
                "\n" +
                "Information collected from other sources\n" +
                "\n" +
                "In Short:  We may collect limited data from public databases, marketing partners, social media platforms, and other outside sources.\n" +
                "\n" +
                "In order to enhance our ability to provide relevant marketing, offers and services to you and update our records, we may obtain information about you from other sources, such as public databases, joint marketing partners, affiliate programs, data providers, social media platforms, as well as from other third parties. This information includes mailing addresses, job titles, email addresses, phone numbers, intent data (or user behavior data), Internet Protocol (IP) addresses, social media profiles, social media URLs and custom profiles, for purposes of targeted advertising and event promotion. If you interact with us on a social media platform using your social media account (e.g. Facebook or Twitter), we receive personal information about you such as your name, email address, and gender. Any personal information that we collect from your social media account depends on your social media account's privacy settings.\n" +
                "\n" +
                "2. HOW DO WE USE YOUR INFORMATION?\n" +
                "\n" +
                "In Short:  We process your information for purposes based on legitimate business interests, the fulfillment of our contract with you, compliance with our legal obligations, and/or your consent.\n" +
                "\n" +
                "We use personal information collected via our App for a variety of business purposes described below. We process your personal information for these purposes in reliance on our legitimate business interests, in order to enter into or perform a contract with you, with your consent, and/or for compliance with our legal obligations. We indicate the specific processing grounds we rely on next to each purpose listed below.\n" +
                "\n" +
                "We use the information we collect or receive:\n" +
                "To facilitate account creation and logon process. If you choose to link your account with us to a third-party account (such as your Google or Facebook account), we use the information you allowed us to collect from those third parties to facilitate account creation and logon process for the performance of the contract. See the section below headed \"HOW DO WE HANDLE YOUR SOCIAL LOGINS\" for further information.\n" +
                "To post testimonials. We post testimonials on our App that may contain personal information. Prior to posting a testimonial, we will obtain your consent to use your name and the content of the testimonial. If you wish to update, or delete your testimonial, please contact us at __________ and be sure to include your name, testimonial location, and contact information.\n" +
                "Request feedback. We may use your information to request feedback and to contact you about your use of our App.\n" +
                "To enable user-to-user communications. We may use your information in order to enable user-to-user communications with each user's consent.\n" +
                "To manage user accounts. We may use your information for the purposes of managing our account and keeping it in working order.\n" +
                "To send administrative information to you. We may use your personal information to send you product, service and new feature information and/or information about changes to our terms, conditions, and policies.\n" +
                "To protect our Services. We may use your information as part of our efforts to keep our App safe and secure (for org, for fraud monitoring and prevention).\n" +
                "To enforce our terms, conditions and policies for business purposes, to comply with legal and regulatory requirements or in connection with our contract.\n" +
                "To respond to legal requests and prevent harm. If we receive a subpoena or other legal request, we may need to inspect the data we hold to determine how to respond.\n" +
                "\n" +
                "Fulfill and manage your orders. We may use your information to fulfill and manage your orders, payments, returns, and exchanges made through the App.\n" +
                "\n" +
                "Administer prize draws and competitions. We may use your information to administer prize draws and competitions when you elect to participate in our competitions.\n" +
                "\n" +
                "To deliver and facilitate delivery of services to the user. We may use your information to provide you with the requested service.\n" +
                "\n" +
                "To respond to user inquiries/offer support to users. We may use your information to respond to your inquiries and solve any potential issues you might have with the use of our Services.\n" +
                "To send you marketing and promotional communications. We and/or our third-party marketing partners may use the personal information you send to us for our marketing purposes, if this is in accordance with your marketing preferences. For org, when expressing an interest in obtaining information about us or our App, subscribing to marketing or otherwise contacting us, we will collect personal information from you. You can opt-out of our marketing emails at any time (see the \"WHAT ARE YOUR PRIVACY RIGHTS\" below).\n" +
                "Deliver targeted advertising to you. We may use your information to develop and display personalized content and advertising (and work with third parties who do so) tailored to your interests and/or location and to measure its effectiveness.\n" +
                "For other business purposes. We may use your information for other business purposes, such as data analysis, identifying usage trends, determining the effectiveness of our promotional campaigns and to evaluate and improve our App, products, marketing and your experience. We may use and store this information in aggregated and anonymized form so that it is not associated with individual end users and does not include personal information. We will not use identifiable personal information without your consent.\n" +
                "\n" +
                "3. WILL YOUR INFORMATION BE SHARED WITH ANYONE?\n" +
                "\n" +
                "In Short:  We only share information with your consent, to comply with laws, to provide you with services, to protect your rights, or to fulfill business obligations.\n" +
                "\n" +
                "We may process or share your data that we hold based on the following legal basis:\n" +
                "Consent: We may process your data if you have given us specific consent to use your personal information for a specific purpose.\n" +
                "Legitimate Interests: We may process your data when it is reasonably necessary to achieve our legitimate business interests.\n" +
                "Performance of a Contract: Where we have entered into a contract with you, we may process your personal information to fulfill the terms of our contract.\n" +
                "Legal Obligations: We may disclose your information where we are legally required to do so in order to comply with applicable law, governmental requests, a judicial proceeding, court order, or legal process, such as in response to a court order or a subpoena (including in response to public authorities to meet national security or law enforcement requirements).\n" +
                "Vital Interests: We may disclose your information where we believe it is necessary to investigate, prevent, or take action regarding potential violations of our policies, suspected fraud, situations involving potential threats to the safety of any person and illegal activities, or as evidence in litigation in which we are involved.\n" +
                "More specifically, we may need to process your data or share your personal information in the following situations:\n" +
                "Business Transfers. We may share or transfer your information in connection with, or during negotiations of, any merger, sale of company assets, financing, or acquisition of all or a portion of our business to another company.\n" +
                "Other Users. When you share personal information (for org, by posting comments, contributions or other content to the App) or otherwise interact with public areas of the App, such personal information may be viewed by all users and may be publicly made available outside the App in perpetuity. If you interact with other users of our App and register for our App through a social network (such as Facebook), your contacts on the social network will see your name, profile photo, and descriptions of your activity. Similarly, other users will be able to view descriptions of your activity, communicate with you within our App, and view your profile.\n" +
                "Offer Wall. Our App may display a third-party hosted \"offer wall.\" Such an offer wall allows third-party advertisers to offer virtual currency, gifts, or other items to users in return for the acceptance and completion of an advertisement offer. Such an offer wall may appear in our App and be displayed to you based on certain data, such as your geographic area or demographic information. When you click on an offer wall, you will be brought to an external website belonging to other persons and will leave our App. A unique identifier, such as your user ID, will be shared with the offer wall provider in order to prevent fraud and properly credit your account with the relevant reward. Please note that we do not control third-party websites and have no responsibility in relation to the content of such websites. The inclusion of a link towards a third-party website does not imply an endorsement by us of such website. Accordingly, we do not make any warranty regarding such third-party websites and we will not be liable for any loss or damage caused by the use of such websites. In addition, when you access any third-party website, please understand that your rights while accessing and using those websites will be governed by the privacy notice and terms of service relating to the use of those websites.\n" +
                "\n" +
                "4. DO WE USE COOKIES AND OTHER TRACKING TECHNOLOGIES?\n" +
                "\n" +
                "In Short:  We may use cookies and other tracking technologies to collect and store your information.\n" +
                "\n" +
                "We may use cookies and similar tracking technologies (like web beacons and pixels) to access or store information. Specific information about how we use such technologies and how you can refuse certain cookies is set out in our Cookie Notice.\n" +
                "\n" +
                "5. DO WE USE GOOGLE MAPS PLATFORM APIS?\n" +
                "\n" +
                "In Short:  Yes, we use Google Maps Platform APIs for the purpose of providing better service.\n" +
                "\n" +
                "This App uses Google Maps Platform APIs which are subject to Google’s Terms of Service. You may find the Google Maps Platform Terms of Service here. To find out more about Google’s Privacy Policy, please refer to this link.\n" +
                "\n" +
                "We use certain Google Maps Platform APIs to retrieve certain information when you make location-specific requests. This includes:\n" +
                "Geolocations\n" +
                "For a full list of what we use information for, please see the previous section titled \"HOW DO WE USE YOUR INFORMATION?\" and \"WILL YOUR INFORMATION BE SHARED WITH ANYONE?\". We obtain and store on your device ('cache') your location. You may revoke your consent anytime by contacting us at the contact details provided at the end of this document.\n" +
                "\n" +
                "The Google Maps Platform APIs that we use store and access cookies and other information on your devices. If you are a user currently in the European Economic Area (EU countries, Iceland, Liechtenstein and Norway), please take a look at our Cookie Notice.\n" +
                "\n" +
                "6. HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                "\n" +
                "In Short:  If you choose to register or log in to our services using a social media account, we may have access to certain information about you.\n" +
                "\n" +
                "Our App offers you the ability to register and login using your third-party social media account details (like your Facebook or Twitter logins). Where you choose to do this, we will receive certain profile information about you from your social media provider. The profile Information we receive may vary depending on the social media provider concerned, but will often include your name, email address, friends list, profile picture as well as other information you choose to make public on such social media platform.\n" +
                "\n" +
                "We will use the information we receive only for the purposes that are described in this privacy notice or that are otherwise made clear to you on the relevant App. Please note that we do not control, and are not responsible for, other uses of your personal information by your third-party social media provider. We recommend that you review their privacy notice to understand how they collect, use and share your personal information, and how you can set your privacy preferences on their sites and apps.\n" +
                "\n" +
                "7. WHAT IS OUR STANCE ON THIRD-PARTY WEBSITES?\n" +
                "\n" +
                "In Short:  We are not responsible for the safety of any information that you share with third-party providers who advertise, but are not affiliated with, our Website.\n" +
                "\n" +
                "The App may contain advertisements from third parties that are not affiliated with us and which may link to other websites, online services or mobile applications. We cannot guarantee the safety and privacy of data you provide to any third parties. Any data collected by third parties is not covered by this privacy notice. We are not responsible for the content or privacy and security practices and policies of any third parties, including other websites, services or applications that may be linked to or from the App. You should review the policies of such third parties and contact them directly to respond to your questions.\n" +
                "\n" +
                "8. HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                "\n" +
                "In Short:  We keep your information for as long as necessary to fulfill the purposes outlined in this privacy notice unless otherwise required by law.\n" +
                "\n" +
                "We will only keep your personal information for as long as it is necessary for the purposes set out in this privacy notice, unless a longer retention period is required or permitted by law (such as tax, accounting or other legal requirements). No purpose in this notice will require us keeping your personal information for longer than the period of time in which users have an account with us.\n" +
                "\n" +
                "When we have no ongoing legitimate business need to process your personal information, we will either delete or anonymize such information, or, if this is not possible (for org, because your personal information has been stored in backup archives), then we will securely store your personal information and isolate it from any further processing until deletion is possible.\n" +
                "\n" +
                "9. HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                "\n" +
                "In Short:  We aim to protect your personal information through a system of organizational and technical security measures.\n" +
                "\n" +
                "We have implemented appropriate technical and organizational security measures designed to protect the security of any personal information we process. However, despite our safeguards and efforts to secure your information, no electronic transmission over the Internet or information storage technology can be guaranteed to be 100% secure, so we cannot promise or guarantee that hackers, cybercriminals, or other unauthorized third parties will not be able to defeat our security, and improperly collect, access, steal, or modify your information. Although we will do our best to protect your personal information, transmission of personal information to and from our App is at your own risk. You should only access the App within a secure environment.\n" +
                "\n" +
                "10. DO WE COLLECT INFORMATION FROM MINORS?\n" +
                "\n" +
                "In Short:  We do not knowingly collect data from or market to children under 18 years of age.\n" +
                "\n" +
                "We do not knowingly solicit data from or market to children under 18 years of age. By using the App, you represent that you are at least 18 or that you are the parent or guardian of such a minor and consent to such minor dependent’s use of the App. If we learn that personal information from users less than 18 years of age has been collected, we will deactivate the account and take reasonable measures to promptly delete such data from our records. If you become aware of any data we may have collected from children under age 18, please contact us at __________.\n" +
                "\n" +
                "11. WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                "\n" +
                "In Short:  You may review, change, or terminate your account at any time.\n" +
                "\n" +
                "If you are a resident in the European Economic Area and you believe we are unlawfully processing your personal information, you also have the right to complain to your local data protection supervisory authority. You can find their contact details here: http://ec.europa.eu/justice/data-protection/bodies/authorities/index_en.htm.\n" +
                "\n" +
                "If you are a resident in Switzerland, the contact details for the data protection authorities are available here: https://www.edoeb.admin.ch/edoeb/en/home.html.\n" +
                "\n" +
                "Account Information\n" +
                "\n" +
                "If you would at any time like to review or change the information in your account or terminate your account, you can:\n" +
                "Log in to your account settings and update your user account.\n" +
                "Contact us using the contact information provided.\n" +
                "Upon your request to terminate your account, we will deactivate or delete your account and information from our active databases. However, we may retain some information in our files to prevent fraud, troubleshoot problems, assist with any investigations, enforce our Terms of Use and/or comply with applicable legal requirements.\n" +
                "\n" +
                "Cookies and similar technologies: Most Web browsers are set to accept cookies by default. If you prefer, you can usually choose to set your browser to remove cookies and to reject cookies. If you choose to remove cookies or reject cookies, this could affect certain features or services of our App. To opt-out of interest-based advertising by advertisers on our App visit http://www.aboutads.info/choices/.\n" +
                "\n" +
                "Opting out of email marketing: You can unsubscribe from our marketing email list at any time by clicking on the unsubscribe link in the emails that we send or by contacting us using the details provided below. You will then be removed from the marketing email list — however, we may still communicate with you, for org to send you service-related emails that are necessary for the administration and use of your account, to respond to service requests, or for other non-marketing purposes. To otherwise opt-out, you may:\n" +
                "Access your account settings and update your preferences.\n" +
                "Contact us using the contact information provided.\n" +
                "\n" +
                "12. CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                "\n" +
                "Most web browsers and some mobile operating systems and mobile applications include a Do-Not-Track (\"DNT\") feature or setting you can activate to signal your privacy preference not to have data about your online browsing activities monitored and collected. At this stage no uniform technology standard for recognizing and implementing DNT signals has been finalized. As such, we do not currently respond to DNT browser signals or any other mechanism that automatically communicates your choice not to be tracked online. If a standard for online tracking is adopted that we must follow in the future, we will inform you about that practice in a revised version of this privacy notice.\n" +
                "\n" +
                "13. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                "\n" +
                "In Short:  Yes, if you are a resident of California, you are granted specific rights regarding access to your personal information.\n" +
                "\n" +
                "California Civil Code Section 1798.83, also known as the \"Shine The Light\" law, permits our users who are California residents to request and obtain from us, once a year and free of charge, information about categories of personal information (if any) we disclosed to third parties for direct marketing purposes and the names and addresses of all third parties with which we shared personal information in the immediately preceding calendar year. If you are a California resident and would like to make such a request, please submit your request in writing to us using the contact information provided below.\n" +
                "\n" +
                "If you are under 18 years of age, reside in California, and have a registered account with the App, you have the right to request removal of unwanted data that you publicly post on the App. To request removal of such data, please contact us using the contact information provided below, and include the email address associated with your account and a statement that you reside in California. We will make sure the data is not publicly displayed on the App, but please be aware that the data may not be completely or comprehensively removed from all our systems (e.g. backups, etc.).\n" +
                "\n" +
                "14. DO WE MAKE UPDATES TO THIS NOTICE?\n" +
                "\n" +
                "In Short:  Yes, we will update this notice as necessary to stay compliant with relevant laws.\n" +
                "\n" +
                "We may update this privacy notice from time to time. The updated version will be indicated by an updated \"Revised\" date and the updated version will be effective as soon as it is accessible. If we make material changes to this privacy notice, we may notify you either by prominently posting a notice of such changes or by directly sending you a notification. We encourage you to review this privacy notice frequently to be informed of how we are protecting your information.\n" +
                "\n" +
                "15. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\n" +
                "\n" +
                "If you have questions or comments about this notice, you may email us at __________ or by post to:\n" +
                "\n" +
                "Clinify\n" +
                "__________\n" +
                "__________\n" +
                "India\n" +
                "\n" +
                "16. HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU?\n" +
                "\n" +
                "Based on the applicable laws of your country, you may have the right to request access to the personal information we collect from you, change that information, or delete it in some circumstances. To request to review, update, or delete your personal information, please visit: __________. We will respond to your request within 30 days.\n" +
                "This privacy policy was created using Termly’s Privacy Policy Generator.";

        TextView tv =v.findViewById(R.id.txt);
        tv.setText(str);
        return v;
    }
}