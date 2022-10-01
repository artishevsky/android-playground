package com.artishevsky.android.playground.feature.creatures.view.avatars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.artishevsky.android.playground.databinding.LayoutAvatarBottomSheetBinding
import com.artishevsky.android.playground.feature.creatures.model.Avatar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AvatarBottomDialogFragment : BottomSheetDialogFragment(), AvatarAdapter.AvatarListener {

    private lateinit var callback: AvatarAdapter.AvatarListener

    private var _binding: LayoutAvatarBottomSheetBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutAvatarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.avatarRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.avatarRecyclerView.adapter = AvatarAdapter(AvatarStore.AVATARS, this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            callback = activity as AvatarAdapter.AvatarListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${activity.toString()} must implement AvatarAdapter.AvatarListener")
        }
    }

    override fun avatarClicked(avatar: Avatar) {
        callback.avatarClicked(avatar)
    }

    companion object {
        fun newInstance(): AvatarBottomDialogFragment {
            return AvatarBottomDialogFragment()
        }
    }
}