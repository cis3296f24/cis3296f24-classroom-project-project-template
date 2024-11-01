//
//  ContentView.swift
//  SplitDiff
//
//  Created by Phuykong on 10/28/24.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Text("🧾")
                .font(.system(size: 24))
            Text("Split Diff!")
                .font(.headline)
            
            Button("Take a Picture") {
                print("Took a Picture!")
            }
            .padding(.top, 20)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
