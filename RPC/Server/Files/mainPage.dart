// ignore: file_names
// ignore_for_file: file_names, must_be_immutable, use_key_in_widget_constructors

import 'package:flutter/material.dart';
import '../Components/iconButton.dart';
import '../Components/appBar.dart';
import '../Models/ButtonComponents.dart';
import 'Explore.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class MainPage extends StatefulWidget {
  var buttonComponents = <ButtonComponents>[];

  MainPage() {
    buttonComponents = [];
    buttonComponents.add(ButtonComponents(
        icon: const Icon(Icons.search), interestPointText: 'Explorar'));
    buttonComponents.add(ButtonComponents(
        icon: const Icon(Icons.map), interestPointText: 'Mapa'));
    buttonComponents.add(ButtonComponents(
        icon: const FaIcon(FontAwesomeIcons.twitter),
        interestPointText: 'Fauna'));
    buttonComponents.add(ButtonComponents(
        icon: const FaIcon(FontAwesomeIcons.leaf), interestPointText: 'Flora'));
  }

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  dono() {
    return 0;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: PreferredSize(
            preferredSize: const Size.fromHeight(60),
            child: bar(
              title: const Text(''),
              backButtonFunction: dono(),
            )),
        body: SingleChildScrollView(
            child: Center(
                child: SizedBox(
                    height: 200,
                    child: Column(
                      //mainAxisAlignment: MainAxisAlignment.end,
                      children: <Widget>[
                        Container(
                            constraints: const BoxConstraints(
                                minWidth: double.infinity, minHeight: 300.0),
                            decoration: BoxDecoration(
                                border:
                                    Border.all(color: Colors.black, width: 1.0),
                                color: Colors.white)),
                        ListView.builder(
                            itemCount: widget.buttonComponents.length,
                            itemBuilder: (BuildContext context, int index) {
                              return iconButton(
                                buttonFunction: Explore(),
                                icon: widget.buttonComponents[index].icon,
                                interestPointText: widget
                                    .buttonComponents[index].interestPointText,
                              );
                            })
                      ],
                    )))));
  }
}
